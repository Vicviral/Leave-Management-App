package com.victorloveday.leavemanager.api

import com.victorloveday.leavemanager.database.model.HistoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LeaveApi {

    @GET("/leave/route/outgoing.php?")
    suspend fun getLeaves(
        @Query("employeeLeaves") employeeLeaves: String,
        @Query("userId") userId: String,
    ): Response<HistoryResponse>
}