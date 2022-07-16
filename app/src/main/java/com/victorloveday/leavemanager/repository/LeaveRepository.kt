package com.victorloveday.leavemanager.repository

import androidx.lifecycle.LiveData
import com.victorloveday.leavemanager.database.LeaveDao
import com.victorloveday.leavemanager.database.model.Leave

class LeaveRepository(private val leaveDao: LeaveDao) {

    //return every item on the leave table
    val readAllLeaveHistory: LiveData<List<Leave>> = leaveDao.getAllLeaveHistory()
    val readRecentFiveLeaves: LiveData<List<Leave>> = leaveDao.getRecentFiveLeaves()

    suspend fun saveLeave(leave: Leave) {
        leaveDao.saveLeave(leave)
    }

    suspend fun deleteLeave(leave: Leave) {
        leaveDao.deleteLeave(leave)
    }

    fun getLeaveHistoryByLeaveType(leaveType: String): LiveData<List<Leave>> {
        return leaveDao.getLeaveHistoryByLeaveType(leaveType)
    }

    fun getRecentPendingLeave(status: String): LiveData<List<Leave>> {
        return leaveDao.getRecentPendingLeave(status)
    }

}