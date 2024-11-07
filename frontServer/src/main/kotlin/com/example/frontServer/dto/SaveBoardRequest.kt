package com.example.frontServer.dto

import jakarta.validation.constraints.NotEmpty

data class SaveBoardRequest(
    @field:NotEmpty
    val textContent: String,

    val files: FilesDto? = null,
    )
//