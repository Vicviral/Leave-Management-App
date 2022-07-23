package com.victorloveday.leavemanager.repository

import androidx.lifecycle.LiveData
import com.victorloveday.leavemanager.database.LeaveDao
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.database.model.Notification

class LeaveRepository(private val leaveDao: LeaveDao) {

    //return every item on the leave table
    val readAllLeaveHistory: LiveData<List<Leave>> = leaveDao.getAllLeaveHistory()
    val readAllNotifications: LiveData<List<Notification>> = leaveDao.getAllNotifications()

    suspend fun saveLeave(leave: List<Leave>) {
        leaveDao.saveLeave(leave)
    }

    suspend fun deleteLeave(leave: Leave) {
        leaveDao.deleteLeave(leave)
    }

    fun getLeaveHistoryByLeaveTypeAndUserId(leaveType: String, userId: String): LiveData<List<Leave>> {
        return leaveDao.getLeaveHistoryByLeaveTypeAndUserId(leaveType, userId)
    }

    fun getRecentPendingLeave(status: String, userId: String): LiveData<List<Leave>> {
        return leaveDao.getRecentPendingLeave(status, userId)
    }

    fun getRecentFiveLeaves(userId: String): LiveData<List<Leave>> {
        return leaveDao.getRecentFiveLeaves(userId)
    }

    fun getLeavesByStatusAndType(leaveType: String, status: String, userId: String): LiveData<List<Leave>> {
        return leaveDao.getLeavesByStatusAndType(leaveType, status, userId)
    }

    fun getAllLeaveHistoryByUserId(userId: String): LiveData<List<Leave>> {
        return leaveDao.getAllLeaveHistoryByUserId(userId)
    }

    //notifications
    suspend fun saveNotification(notification: List<Notification>) {
        leaveDao.saveNotification(notification)
    }


}