package com.victorloveday.leavemanager.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.victorloveday.leavemanager.database.UserDatabase
import com.victorloveday.leavemanager.database.model.User
import com.victorloveday.leavemanager.repository.EmployeesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeesViewModel(application: Application) : AndroidViewModel(application) {
    val readAllEmployees: LiveData<List<User>>
    private val employeesRepository: EmployeesRepository


    init {
        val employeesDao = UserDatabase.getDatabase(application).employeesDao()
        employeesRepository = EmployeesRepository(employeesDao)
        readAllEmployees = employeesRepository.readAllEmployees
    }

    fun saveEmployees(user: List<User>) {
        viewModelScope.launch(Dispatchers.IO) {
            employeesRepository.saveEmployees(user)
        }
    }

    fun getEmployeeByUserIdAndPassword(userId: String, password: String): LiveData<User> {
        return employeesRepository.getEmployeeByUserIdAndPassword(userId, password)
    }

}