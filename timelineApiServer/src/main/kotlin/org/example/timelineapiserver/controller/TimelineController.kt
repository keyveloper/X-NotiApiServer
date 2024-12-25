package org.example.timelineapiserver.controller

import jakarta.validation.Valid
import org.example.timelineapiserver.dto.request.TimelineGetRequest
import org.example.timelineapiserver.dto.request.TimelineSaveRequest
import org.example.timelineapiserver.dto.response.TimelineServerGetResponse
import org.example.timelineapiserver.dto.response.TimelineServerSaveResponse
import org.example.timelineapiserver.enum.MSAServerErrorCode
import org.example.timelineapiserver.service.TimelineService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TimelineController(
    private val timelineService: TimelineService,
) {
    @PostMapping("/saveTimeline")
    fun save(
        @Valid @RequestBody request: List<TimelineSaveRequest>
    ): ResponseEntity<TimelineServerSaveResponse> {
        timelineService.save(request)
        return ResponseEntity.ok().body(
            TimelineServerSaveResponse(
                errorDetails = null,
                errorCode = MSAServerErrorCode.SUCCESS
            )
        )
    }

    @PostMapping("/getTimeline/init")
    fun findTimelineInit(
        @Valid @RequestBody request: TimelineGetRequest
    ): ResponseEntity<TimelineServerGetResponse> {
        return ResponseEntity.ok().body(
            TimelineServerGetResponse.of(
                timelineService.findTimelineInit(request),
                errorDetails = null,
                errorCode = MSAServerErrorCode.SUCCESS
            )
        )
    }

    @PostMapping("/getTimeline/next")
    fun findTimelineNext(
        @Valid @RequestBody request: TimelineGetRequest
    ): ResponseEntity<TimelineServerGetResponse> {
        return ResponseEntity.ok().body(
            TimelineServerGetResponse.of(
                timelineService.findTimelineNext(request),
                errorDetails = null,
                errorCode = MSAServerErrorCode.SUCCESS
            )
        )
    }

    @PostMapping("/getTimeline/prev")
    fun findTimelinePrev(
        @Valid @RequestBody request: TimelineGetRequest
    ): ResponseEntity<TimelineServerGetResponse> {
        return ResponseEntity.ok().body(
            TimelineServerGetResponse.of(
                timelineService.findTimelinePrev(request),
                errorDetails = null,
                errorCode = MSAServerErrorCode.SUCCESS
            )
        )
    }
}