package com.victorloveday.leavemanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notification_table")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val body: String,
    val date: String,
    val time: String,
)
