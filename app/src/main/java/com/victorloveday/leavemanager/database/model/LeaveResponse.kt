package com.victorloveday.leavemanager.database.model

data class LeaveResponse(
    val leaves: MutableList<Leave>,
    val status: String,
)