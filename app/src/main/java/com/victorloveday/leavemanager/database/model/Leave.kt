package com.victorloveday.leavemanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "leaves_table")
data class Leave(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val title: String?,
    val type: String?,
    val reason: String?,
    val startDate: String?,
    val endDate: String?,
    val status: String?
)
