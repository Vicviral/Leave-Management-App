package com.example.newdo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.victorloveday.leavemanager.database.LeaveDao
import com.victorloveday.leavemanager.database.model.Leave

@Database(
    entities = [Leave::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getArticleDao(): LeaveDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_db.db"
            ).build()
    }
}