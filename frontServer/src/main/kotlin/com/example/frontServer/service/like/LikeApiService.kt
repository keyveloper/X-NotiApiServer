package com.example.frontServer.service.like

import com.example.frontServer.config.WebConfig
import com.example.frontServer.dto.like.LikeSaveRequest
import com.example.frontServer.dto.like.LikeSaveRequestToServer
import com.example.frontServer.dto.like.LikeSaveResult
import com.example.frontServer.dto.like.LikeServerSaveResponse
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.util.UriBuilder

@Service
class LikeApiService(
    private val webConfig: WebConfig,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("likeApiCircuitBreaker")
    private val logger = KotlinLogging.logger {}
    private val baseUrl = "http://localhost:8082"

    @CircuitBreaker(
        name = "likeApiCircuitBreaker",
        fallbackMethod = "saveFallbackMethod")
    fun save(likeRequest: LikeSaveRequest, userId: Long) {
        val likeServerWebClient = webConfig.createWebClient(
            baseUrl = baseUrl
        )
        val response = likeServerWebClient.post()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder
                    .path("/like")
                    .build()
            }
            .bodyValue(
                LikeSaveRequestToServer(
                    boardId = likeRequest.boardId,
                    userId = userId,
                    likeType = likeRequest.likeType,
                )
            )
            .headers { headers ->
                headers.remove(HttpHeaders.AUTHORIZATION)
            }
            .retrieve()
            .bodyToMono(LikeServerSaveResponse::class.java)
            .block()
    }

    @CircuitBreaker(
        name = "likeApiCircuitBreaker",
        fallbackMethod = "likeCountFallback"
    )
    fun fetchLikeCountByBoardId(boardId: Long): Long {
        return 0 // 나중에 업데이트 ㄱㄱ
    }

    // save 메서드의 fallbackMethod
    // 다시 만들기
    fun saveFallbackMethod(boardId: Long, userId: Long, throwable: Throwable): LikeSaveResult {
        logger.error { "Fallback called in save due to ${throwable.message}" }
        logCircuitBreakerInfo()
        return LikeSaveResult.of(
            LikeServerSaveResponse(
                error = null,
                details = null
            )
        )
    }

    fun likeCountFallbackMethod(boardId: Long, t: Throwable): Long {
        return -1
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