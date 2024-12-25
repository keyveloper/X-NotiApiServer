package org.example.timelineapiserver.repository

import org.example.timelineapiserver.dto.request.TimelineGetRequest
import org.example.timelineapiserver.entity.Timeline

interface TimelineQueryDslRepository {
    fun findAllInit(timelineRequest: TimelineGetRequest): List<Timeline>

    fun findAllNext(timelineRequest: TimelineGetRequest): List<Timeline>

    fun findAllPrev(timelineRequest: TimelineGetRequest): List<Timeline>
}