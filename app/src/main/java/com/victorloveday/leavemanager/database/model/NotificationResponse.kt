package com.victorloveday.leavemanager.database.model

data class NotificationResponse(
    val info: List<Notification>,
    val status: Int
)