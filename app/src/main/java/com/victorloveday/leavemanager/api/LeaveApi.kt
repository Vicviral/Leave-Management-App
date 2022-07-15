package com.victorloveday.leavemanager.api

import com.victorloveday.leavemanager.database.model.LeaveResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LeaveApi {

    @GET("history")
    suspend fun getLeaves(
        @Query("id") country: String
    ): Response<LeaveResponse>
}