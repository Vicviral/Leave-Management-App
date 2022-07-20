package com.victorloveday.leavemanager.database.model

data class User(
    val name: String,
    val userId: String,
    val age: String,
    val role: String,
    val userType: String,
    val isOnLeave: Boolean
)
