package org.example.timelineapiserver.service

import org.example.timelineapiserver.dto.error.MSAServerErrorDetails
import org.example.timelineapiserver.dto.request.TimelineGetRequest
import org.example.timelineapiserver.dto.request.TimelineSaveRequest
import org.example.timelineapiserver.dto.response.TimelineServerGetResult
import org.example.timelineapiserver.entity.Timeline
import org.example.timelineapiserver.enum.MSAServerErrorCode
import org.example.timelineapiserver.exception.BusinessException
import org.example.timelineapiserver.exception.DuplicatedIdException
import org.example.timelineapiserver.repository.TimelineRepository
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TimelineService(
    private val timelineRepository: TimelineRepository
) {
    // 발생 가능한 오류 : DB 제약 조건 위반, DB 서버 장애,
    @Transactional
    fun save(request: List<TimelineSaveRequest>): Int { // 저장 건수만
        return try {
            timelineRepository.saveAll(
                request.map {
                    Timeline(
                        id = null,
                        receiverId = it.receiverId,
                        boardId = it.boardId,
                        createdAt = null
                    )
                }
            ).size
        } catch (ex: DataIntegrityViolationException) {
            // check duplicated error
            val rootCause = ex.rootCause
            if (rootCause is ConstraintViolationException) {
                if (rootCause.message?.contains("duplicate") == true) {
                    throw DuplicatedIdException(
                        errorDetails = MSAServerErrorDetails(
                            url = "/saveTimeline",
                            status = HttpStatus.BAD_REQUEST,
                            title = "duplicated id inserted!!",
                            detail = "${rootCause.message}"
                        )
                    )
                } else {
                    throw BusinessException(
                        errorCode = MSAServerErrorCode.UNKNOWN_ERROR,
                        errorDetails = MSAServerErrorDetails(
                            url = "/saveTimeline",
                            status = HttpStatus.BAD_REQUEST,
                            title = "unknown business error",
                            detail = "Data integrity violation error, but not defined!" +
                            "${rootCause.message}"
                        )
                    )
                }
            } else {
                throw BusinessException(
                    errorCode = MSAServerErrorCode.UNKNOWN_ERROR,
                    errorDetails = MSAServerErrorDetails(
                        url = "/saveTimeline",
                        status = HttpStatus.BAD_REQUEST,
                        title = "unknown business error",
                        detail = "Data integrity violation error, but not defined!" +
                                "${ex.message}"
                    )
                )
            }
        }
    }

    // empty list = receiverId not exist (implicit)
    fun findTimelineInit(
        request: TimelineGetRequest
    ): List<TimelineServerGetResult> {
        return timelineRepository.findAllInit(request)
            .map {
                TimelineServerGetResult.of(it)
            }
    }

    fun findTimelineNext(
        request: TimelineGetRequest
    ): List<TimelineServerGetResult> {
        return timelineRepository.findAllNext(request)
            .map {
                TimelineServerGetResult.of(it)
            }
    }

    fun findTimelinePrev(
        request: TimelineGetRequest
    ): List<TimelineServerGetResult> {
        return timelineRepository.findAllPrev(request)
            .map {
                TimelineServerGetResult.of(it)
            }
    }
}