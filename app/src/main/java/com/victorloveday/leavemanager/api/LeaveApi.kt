package com.victorloveday.leavemanager.api

import com.victorloveday.leavemanager.database.model.HistoryResponse
import com.victorloveday.leavemanager.database.model.LeaveApplicationResponse
import com.victorloveday.leavemanager.database.model.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LeaveApi {

    @GET("/leave/route/outgoing.php?")
    suspend fun getLeaves(
        @Query("employeeLeaves") employeeLeaves: String,
        @Query("userId") userId: String,
    ): Response<HistoryResponse>

    @GET("/leave/route/outgoing.php?")
    suspend fun submitLeaveApplication(
        @Query("employeeLeaveApplication") employeeLeaveApplication: String,
        @Query("user_id") user_id: String,
        @Query("leave_type") leave_type: String,
        @Query("leave_message") leave_message: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String,
        @Query("leave_title") leave_title: String,
        @Query("leave_duration") leave_duration: String
    ): Response<LeaveApplicationResponse>

    @GET("/leave/route/outgoing.php?")
    suspend fun deleteLeave(
        @Query("leave_application_deletion") leave_application_deletion: String,
        @Query("leave_id") leave_id: String
    ): Response<LeaveApplicationResponse>


    //notifications
    @GET("/leave/route/outgoing.php?")
    suspend fun getNotifications(
        @Query("employeeNotification") employeeNotification: String,
        @Query("userId") userId: String
    ): Response<NotificationResponse>
}