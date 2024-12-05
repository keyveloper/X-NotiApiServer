package com.example.frontServer.service

import com.example.frontServer.dto.LikeRequest
import com.example.frontServer.dto.LikeSaveResult
import com.example.frontServer.dto.LikeServerSaveResponse
import com.example.frontServer.dto.ServiceServerError
import com.example.frontServer.enum.FrontServerError
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder

@Service
class LikeService(
    private val client : WebClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry

) {
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("liveApiCircuitBreaker")
    private val logger = KotlinLogging.logger {}
    @CircuitBreaker(
        name = "liveApiCircuitBreaker",
        fallbackMethod = "saveFallbackMethod")
    fun save(boardId: Long, userId: Long): LikeSaveResult {
        val response = client.post()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder
                    .path("/like")
                    .build()
            }
            .bodyValue(
                LikeRequest(
                    boardId = boardId,
                    userId = userId,
                )
            )
            .headers { headers ->
                headers.remove(HttpHeaders.AUTHORIZATION)
            }
            .retrieve()
            .bodyToMono(LikeServerSaveResponse::class.java)
            .block()

        if (response == null) {
            return LikeSaveResult(
                error = FrontServerError.UNEXPECTED_ERROR
            )
        }

        return if (response.error == null) {
            LikeSaveResult(
                error = null
            )
        } else {
            LikeSaveResult(
                error = FrontServerError.UNKNOWN_ID
                // Server에러 별로 나눠야 하는지 ??
            )
        }
    }

    @CircuitBreaker(
        name = "liveApiCircuitBreaker",
        fallbackMethod = "findAllByBoardIdFallbackMethod"
    )
    fun findAllByBoardId(boardId: Long): List<Long> {
        return  try {
            val response = client.get()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                        .path("/like/users")
                        .queryParam("boardId", boardId)
                        .build()
                }
                .retrieve()
                .bodyToMono(ResponseToServerDto::class.java)
                .block()

            response?.data as? List<Long> ?: emptyList()
        } catch (ex: Exception) {
            logger.error { "Exception caught in findAllByBoardId: ${ex.message}"}
            emptyList()
        }
    }

    // save 메서드의 fallbackMethod
    fun saveFallbackMethod(boardId: Long, userId: Long, throwable: Throwable): LikeSaveResult {
        logger.error { "Fallback called in save due to ${throwable.message}" }
        logCircuitBreakerInfo()
        return LikeSaveResult(
            error = FrontServerError.SERVICE_SERVER_ERROR
        )
    }

    // findAllByBoardId 메서드의 fallbackMethod
    fun findAllByBoardIdFallbackMethod(boardId: Long, throwable: Throwable): List<Long> {
        logger.error { "Fallback called in findAllByBoardId due to ${throwable.message}" }
        logCircuitBreakerInfo()
        return emptyList()
    }

    private fun logCircuitBreakerInfo() {
        val metrics = circuitBreaker.metrics

        logger.info { "CircuitBreaker state: ${circuitBreaker.state}" }
        logger.info { "Number of successful calls: ${metrics.numberOfSuccessfulCalls}" }
        logger.info { "Number of failed calls: ${metrics.numberOfFailedCalls}" }
        logger.info { "Failure rate: ${metrics.failureRate}%" }
    }
}