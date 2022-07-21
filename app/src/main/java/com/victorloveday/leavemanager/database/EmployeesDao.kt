package com.victorloveday.leavemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.database.model.Notification
import com.victorloveday.leavemanager.database.model.User

@Dao
interface EmployeesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEmployees(leave: List<User>)

    @Query("SELECT * FROM employee_table ORDER BY id DESC")
    fun getAllEmployee(): LiveData<List<User>>

}
