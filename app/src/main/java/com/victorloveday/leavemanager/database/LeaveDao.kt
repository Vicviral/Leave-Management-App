package com.victorloveday.leavemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victorloveday.leavemanager.database.model.Leave

@Dao
interface LeaveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLeave(leave: Leave): Long

    @Delete
    suspend fun deleteLeave(leave: Leave)

    @Query("SELECT * FROM leaves_table ORDER BY id DESC")
    fun getAllLeaveHistory(): LiveData<List<Leave>>

    @Query("SELECT * FROM leaves_table WHERE `leaveType` = :leaveType")
    fun getLeaveHistoryByLeaveType(leaveType: String): LiveData<List<Leave>>

}
