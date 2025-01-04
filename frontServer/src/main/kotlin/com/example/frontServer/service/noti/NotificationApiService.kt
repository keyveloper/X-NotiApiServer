package com.example.frontServer.service.noti

import com.example.frontServer.config.WebConfig
import com.example.frontServer.dto.error.MSAServerErrorDetails
import com.example.frontServer.dto.notification.*
import com.example.frontServer.dto.notification.request.NotificationGetRequest
import com.example.frontServer.dto.notification.request.NotificationSaveRequest
import com.example.frontServer.dto.notification.response.NotificationGetResult
import com.example.frontServer.dto.notification.response.NotificationGetServerResponse
import com.example.frontServer.dto.notification.response.NotificationGetServerResult
import com.example.frontServer.dto.notification.response.NotificationServerSaveResponse
import com.example.frontServer.entity.Board
import com.example.frontServer.enum.NotificationType
import com.example.frontServer.enum.MSAServerErrorCode
import com.example.frontServer.repository.board.BoardRepository
import com.example.frontServer.repository.user.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import org.springframework.web.util.UriBuilder
import java.util.*

@Service
class NotificationApiService(
    private val webConfig: WebConfig,
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
    private val userRepository: UserRepository,
    private val boardRepository: BoardRepository,
    private val messageSource: MessageSource
    ) {
    private val circuitBreaker = circuitBreakerRegistry.circuitBreaker("notificationApiCircuitBreaker")
    private val logger = KotlinLogging.logger {}
    private val baseUrl = "http://localhost:8081"

    @CircuitBreaker(
        name = "notificationApiCircuitBreaker",
        fallbackMethod = "saveFallbackMethod"
    )
    // from board
    fun saveRequest(requests: List<NotificationSaveRequest>, language: String) {
        val notiServerWebClient = webConfig.createWebClient(baseUrl, language)

        val response = notiServerWebClient.post()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder
                    .path("/notification/save")
                    .build()
            }
            .bodyValue(
                requests
            )
            .retrieve()
            .bodyToMono(NotificationServerSaveResponse::class.java)
            .block()

        if (response != null && response.errorCode != MSAServerErrorCode.SUCCESS) {
            logger.error { response }
        } else {
            logger.info { response?.errorCode }
        }

    }

    @CircuitBreaker(
        name = "notificationApiCircuitBreaker",
        fallbackMethod = "getInitFallbackMethod"
    )
    fun fetchInitAll(receiverId: Long, language: String): List<NotificationGetResult>  {
        val notiServerWebClient = webConfig.createWebClient(baseUrl, language)
        val response = notiServerWebClient.post()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder
                    .path("/notification/init")
                    .build()
            }
            .bodyValue(
                receiverId
            )
            .retrieve()
            .onStatus({ it.isError }) { clientResponse ->
                // HTTP 상태코드가 에러일 때 처리
                clientResponse.bodyToMono(String::class.java).flatMap { errorBody ->
                    // 이곳에서 에러 내용 로깅
                    logger.error {"HTTP Error: ${clientResponse.statusCode()} / Body: $errorBody"}
                    throw RuntimeException("HTTP Error: ${clientResponse.statusCode()} - $errorBody")
                }
            }
                .bodyToMono(NotificationGetServerResponse::class.java)
            .block()

        val results = response?.notificationGetServerResults ?: emptyList()
        logger.info { "response: $results" }

        return results.map {
            // make message by result's parameter
            val username = findUserNameById(it.publisherId)
            val imgYrl = findUserImgUrlById(it.publisherId)
            val targetBoard = it.targetBoardId?.let {
                id -> boardRepository.findById(id).orElse(null)
            }

            val message = makeMessage(it, username, language, targetBoard)
            NotificationGetResult.of(it, username, imgYrl, message)
        }
        // return noti server response
    }

    @CircuitBreaker(
        name = "notificationApiCircuitBreaker",
        fallbackMethod = "getScrollFallbackMethod"
    )
    // circuit 연결
    fun fetchPrevAll(getRequest: NotificationGetRequest, language: String): List<NotificationGetResult> {
        val notiServerWebClient = webConfig.createWebClient(baseUrl, language)

        val response = notiServerWebClient.post()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder
                    .path("/notification/prev")
                    .build()
            }
            .bodyValue(getRequest)
            .retrieve()
            .bodyToMono(NotificationGetServerResponse::class.java)
            .block()

        val results = response?.notificationGetServerResults ?: emptyList()

        return results.map {
            val username = findUserNameById(it.publisherId)
            val imgYrl = findUserImgUrlById(it.publisherId)
            val targetBoard = it.targetBoardId?.let {
                id -> boardRepository.findById(id).orElse(null)
            }

            val message = makeMessage(it, username, language, targetBoard)
            NotificationGetResult.of(it, username, imgYrl, message)
        }
    }
    @CircuitBreaker(
        name = "notificationApiCircuitBreaker",
        fallbackMethod = "getScrollFallbackMethod"
    )
    // circuit 연결
    fun fetchNextAll(getRequest: NotificationGetRequest, language: String): List<NotificationGetResult> {
        val notiServerWebClient = webConfig.createWebClient(baseUrl, language)

        val response = notiServerWebClient.post()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder
                    .path("/notification/next")
                    .build()
            }
            .bodyValue(
                getRequest
            )
            .retrieve()
            .bodyToMono(NotificationGetServerResponse::class.java)
            .block()

        val results = response?.notificationGetServerResults ?: emptyList()
        return results.map {
            val username = findUserNameById(it.publisherId)
            val imgYrl = findUserImgUrlById(it.publisherId)
            val targetBoard = it.targetBoardId?.let {
                id -> boardRepository.findById(id).orElse(null)
            }

            val message = makeMessage(it, username, language, targetBoard)
            NotificationGetResult.of(it, username, imgYrl, message)
        }
    }

    private fun findUserNameById(id: Long): String{
        val user = userRepository.findById(id).orElse(null)
        return user.username
    }

    private fun findUserImgUrlById(id: Long): String? {
        return userRepository.findById(id).orElse(null).userImg
    }


    private fun makeMessage(
        serverResult: NotificationGetServerResult,
        username: String,
        language: String,
        targetBoard: Board?
    ): String{
        val locale = Locale.forLanguageTag(language)
        return when (serverResult.notificationType) {
            NotificationType.BOARD -> {
                val builder: StringBuilder = StringBuilder()
                val textContent = targetBoard?.textContent?.take(25) ?: "???"
                val message = messageSource
                    .getMessage("noti.BOARD", arrayOf(serverResult.targetBoardId, username), locale)

                builder.append(message + "\n")
                builder.append(textContent)
                builder.toString()
            }

            NotificationType.LIKE -> {
                messageSource.getMessage("noti.LIKE", arrayOf(username, targetBoard), locale)
            }
            NotificationType.FOLLOW -> messageSource.getMessage("noti.FOLLOW", arrayOf(username), locale)
        }
    }

    // fall back pattern : same parameter + Throwable , same return type,
    fun saveFallbackMethod(
        requests: List<*>,
        language: String,
        throwable: Throwable
    ) {
        logCircuitBreakerInfo()
        logger.error {"run notification save fallback method!!\n${throwable.message}}"}
    }

    fun getInitFallbackMethod(
        receiverId: Long,
        language: String,
        throwable: Throwable
    ): List<NotificationGetServerResponse> {
        logger.error {"run getInitFallbackMethod!!\n${throwable.message}"}
        logCircuitBreakerInfo()
        return listOf()
    }

    fun getScrollFallbackMethod(
        getRequest: NotificationGetRequest,
        language: String,
        throwable: Throwable
    ): List<NotificationGetServerResponse> {
        logger.error { "run scrollFallbackMethod!!\n${throwable.message}}" }
        return listOf()
    }

    private fun logCircuitBreakerInfo() {
        val metrics = circuitBreaker.metrics

        logger.info { "CircuitBreaker state: ${circuitBreaker.state}" }
        logger.info { "Number of successful calls: ${metrics.numberOfSuccessfulCalls}" }
        logger.info { "Number of failed calls: ${metrics.numberOfFailedCalls}" }
        logger.info { "Failure rate: ${metrics.failureRate}%" }
    }
}