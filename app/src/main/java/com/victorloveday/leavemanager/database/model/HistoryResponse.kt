package com.victorloveday.leavemanager.database.model

data class HistoryResponse(
    val info: List<Leave>,
    val status: Int
)