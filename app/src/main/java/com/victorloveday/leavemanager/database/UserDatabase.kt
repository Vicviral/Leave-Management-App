package com.victorloveday.leavemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.database.model.Notification
import com.victorloveday.leavemanager.database.model.User

@Database(entities = [Leave::class, Notification::class, User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun leaveDao(): LeaveDao
    abstract fun employeesDao(): EmployeesDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}