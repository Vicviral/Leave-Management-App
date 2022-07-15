package com.victorloveday.leavemanager.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.victorloveday.leavemanager.application.LeaveApplication
import com.victorloveday.leavemanager.database.UserDatabase
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.repository.LeaveRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeaveViewModel(application: Application) : AndroidViewModel(application) {
    val readAllLeaveHistory: LiveData<List<Leave>>
    private val leaveRepository: LeaveRepository


    init {
        val leaveDao = UserDatabase.getDatabase(application).leaveDao()
        leaveRepository = LeaveRepository(leaveDao)
        readAllLeaveHistory = leaveRepository.readAllLeaveHistory
    }

    fun saveLeave(leave: Leave) {
        viewModelScope.launch(Dispatchers.IO) {
            leaveRepository.saveLeave(leave)
        }
    }

    fun deleteLeave(leave: Leave) {
        viewModelScope.launch(Dispatchers.IO) {
            leaveRepository.deleteLeave(leave)
        }
    }

    fun getLeaveHistoryByLeaveType(leaveType: String): LiveData<List<Leave>> {
        return leaveRepository.getLeaveHistoryByLeaveType(leaveType)
    }


    private fun hasInternet(): Boolean {
        val connectivityManager =
            getApplication<LeaveApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true

                    else -> false
                }
            }
        }

        return false
    }


}