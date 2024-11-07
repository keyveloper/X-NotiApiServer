package com.example.frontServer.controller

import com.example.frontServer.dto.ResponseToClientDto
import com.example.frontServer.service.TimelineService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TimelineController(
    private val timelineService: TimelineService
) {
   @GetMapping("/timeline")
   fun findAllByReceiverId(
       @RequestParam receiverId: Long
   ): ResponseEntity<ResponseToClientDto> {
       return ResponseEntity.ok().body(
           ResponseToClientDto(
               errorCode = null,
               data = timelineService.findByReceiverId(receiverId)
           )
       )
   }
}