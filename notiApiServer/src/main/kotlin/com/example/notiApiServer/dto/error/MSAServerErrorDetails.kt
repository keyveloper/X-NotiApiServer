package com.example.notiApiServer.dto.error

import org.springframework.http.HttpStatus

data class MSAServerErrorDetails( // common used
    val type: String?,     // error url
    val status: HttpStatus,      // Server Error Code --> 서버별로 따로 만들기가 어려워
    val title: String?,    // summary of Error
    val detail: String?,   //  (Optional but need)
)