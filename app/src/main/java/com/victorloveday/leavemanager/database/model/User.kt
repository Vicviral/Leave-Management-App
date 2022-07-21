package com.victorloveday.leavemanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "employee_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val userId: String,
    val age: String,
    val role: String,
    val userType: String,
    val isOnLeave: Boolean,
    val isDeactivated: Boolean
)
