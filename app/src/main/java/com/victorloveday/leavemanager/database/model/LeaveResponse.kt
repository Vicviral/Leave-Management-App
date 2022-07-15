package com.victorloveday.leavemanager.database.model

data class LeaveResponse(
    val error: Boolean,
    val message: String,
    val leave: List<Leave>
)