package com.example.notiApiServer.dto

import com.example.notiApiServer.enum.NotificationType

data class NotificationSaveRequest(
    val publisherId: Long,

    val receiverId: Long,

    val notificationType: NotificationType,

    val targetBoardId: Long?,

    )