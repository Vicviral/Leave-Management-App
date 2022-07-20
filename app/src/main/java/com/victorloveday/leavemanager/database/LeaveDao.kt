package com.victorloveday.leavemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.database.model.Notification

@Dao
interface LeaveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLeave(leave: List<Leave>)

    @Delete
    suspend fun deleteLeave(leave: Leave)

    @Query("SELECT * FROM leaves_table ORDER BY id DESC")
    fun getAllLeaveHistory(): LiveData<List<Leave>>

    @Query("SELECT * FROM leaves_table WHERE `leave_type` = :leaveType")
    fun getLeaveHistoryByLeaveType(leaveType: String): LiveData<List<Leave>>

    @Query("SELECT * FROM leaves_table ORDER BY id DESC LIMIT 5")
    fun getRecentFiveLeaves(): LiveData<List<Leave>>

    @Query("SELECT * FROM leaves_table WHERE `status` = :status ORDER BY id DESC LIMIT 1")
    fun getRecentPendingLeave(status: String): LiveData<List<Leave>>

    @Query("SELECT * FROM leaves_table WHERE leave_type = :leaveType AND status = :status ")
    fun getLeavesByStatusAndType(leaveType: String, status: String): LiveData<List<Leave>>


    //notifications

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNotification(leave: List<Notification>)

    @Query("SELECT * FROM notification_table ORDER BY id DESC")
    fun getAllNotifications(): LiveData<List<Notification>>

}
