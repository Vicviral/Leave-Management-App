package com.victorloveday.leavemanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "articles"
)
data class Leave(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val title: String?,
    val leaveType: String?,
    val reason: String?,
    val startDate: String?,
    val endDate: String?,
    val status: String?
) : Serializable
