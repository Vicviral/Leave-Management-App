package com.victorloveday.leavemanager.repository

import androidx.lifecycle.LiveData
import com.victorloveday.leavemanager.database.EmployeesDao
import com.victorloveday.leavemanager.database.model.User

class EmployeesRepository(private val employeesDao: EmployeesDao) {

    //return every item on the user table
    val readAllEmployees: LiveData<List<User>> = employeesDao.getAllEmployee()

    suspend fun saveEmployees(user: List<User>) {
        employeesDao.saveEmployees(user)
    }

    fun getEmployeeByUserIdAndPassword(userId: String, password: String): LiveData<User> {
        return employeesDao.getEmployeeByUserIdAndPassword(userId, password)
    }

}