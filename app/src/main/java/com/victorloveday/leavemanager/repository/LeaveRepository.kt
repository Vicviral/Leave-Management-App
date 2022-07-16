package com.victorloveday.leavemanager.repository

import androidx.lifecycle.LiveData
import com.victorloveday.leavemanager.api.RetrofitInstance
import com.victorloveday.leavemanager.database.LeaveDao
import com.victorloveday.leavemanager.database.model.Leave

class LeaveRepository(private val leaveDao: LeaveDao) {

    //return every item on the leave table
    val readAllLeaveHistory: LiveData<List<Leave>> = leaveDao.getAllLeaveHistory()
    val readLastFiveLeaves: LiveData<List<Leave>> = leaveDao.getLastFiveLeaves()

    suspend fun saveLeave(leave: Leave) {
        leaveDao.saveLeave(leave)
    }

    suspend fun deleteLeave(leave: Leave) {
        leaveDao.deleteLeave(leave)
    }

    fun getLeaveHistoryByLeaveType(leaveType: String): LiveData<List<Leave>> {
        return leaveDao.getLeaveHistoryByLeaveType(leaveType)
    }

}