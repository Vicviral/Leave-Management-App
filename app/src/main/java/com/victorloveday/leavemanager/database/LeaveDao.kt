package com.victorloveday.leavemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victorloveday.leavemanager.database.model.Leave

@Dao
interface LeaveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(leave: Leave): Long

    @Query("SELECT * FROM articles")
    fun getAllLeaves(): LiveData<List<Leave>>

    @Delete
    suspend fun deleteLeave(leave: Leave)

}
