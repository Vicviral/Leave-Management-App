package com.victorloveday.leavemanager.repository

import androidx.lifecycle.LiveData
import com.victorloveday.leavemanager.database.LeaveDao
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.database.model.Notification

class LeaveRepository(private val leaveDao: LeaveDao) {

    //return every item on the leave table
    val readAllLeaveHistory: LiveData<List<Leave>> = leaveDao.getAllLeaveHistory()
    val readRecentFiveLeaves: LiveData<List<Leave>> = leaveDao.getRecentFiveLeaves()
    val readAllNotifications: LiveData<List<Notification>> = leaveDao.getAllNotifications()

    suspend fun saveLeave(leave: List<Leave>) {
        leaveDao.saveLeave(leave)
    }

    suspend fun deleteLeave(leave: Leave) {
        leaveDao.deleteLeave(leave)
    }

    fun getLeaveHistoryByLeaveType(leaveType: String): LiveData<List<Leave>> {
        return leaveDao.getLeaveHistoryByLeaveTypeAndUserId(leaveType)
    }

    fun getRecentPendingLeave(status: String): LiveData<List<Leave>> {
        return leaveDao.getRecentPendingLeave(status)
    }

    fun getLeavesByStatusAndType(leaveType: String, status: String): LiveData<List<Leave>> {
        return leaveDao.getLeavesByStatusAndType(leaveType, status)
    }

    fun getAllLeaveHistoryByUserId(userId: String): LiveData<List<Leave>> {
        return leaveDao.getAllLeaveHistoryByUserId(userId)
    }

    //notifications
    suspend fun saveNotification(notification: List<Notification>) {
        leaveDao.saveNotification(notification)
    }


}