package com.victorloveday.leavemanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "leaves_table")
data class Leave(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val approved_date: String,
    val declined_date: String,
    val end_date: String,
    val leave_message: String,
    val leave_title: String,
    val leave_type: String,
    val principal_officer: String,
    val start_date: String,
    val status: String,
    val timeIn: String,
    val user_id: String
)
