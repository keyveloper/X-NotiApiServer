package org.example.timelineapiserver.dto.error

import org.springframework.http.HttpStatus

data class MSAServerErrorDetails(
    val url: String?,
    val status: HttpStatus,// original http code ...
    val title: String?,
    val detail: String
)