package com.victorloveday.leavemanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "employee_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val userId: String,
    val age: String,
    val role: String,
    val gender: String,
    val nationality: String,
    val userType: String,
    val isOnLeave: Boolean,
    val isDeactivated: Boolean,
    val password: String
): Serializable
