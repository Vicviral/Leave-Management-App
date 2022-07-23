package com.victorloveday.leavemanager.database

import android.content.Context
import androidx.datastore.preferences.clear
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserInfoManager(context: Context) {
    private val dataStore = context.createDataStore(name = "user_preferences")

    companion object {
        val NAME_KEY = preferencesKey<String>("NAME")
        val USER_ID_KEY = preferencesKey<String>("USER_ID")
        val AGE_KEY = preferencesKey<String>("AGE")
        val ROLE_KEY = preferencesKey<String>("ROLE")
        val USER_TYPE_KEY = preferencesKey<String>("USER_TYPE")
        val USER_ON_LEAVE_KEY = preferencesKey<Boolean>("USER_ON_LEAVE")
        val USER_DEACTIVATED_KEY = preferencesKey<Boolean>("USER_DEACTIVATED")
        val IS_USER_LOGGED_IN_KEY = preferencesKey<Boolean>("IS_USER_LOGGED_IN")
    }

    suspend fun clearUser() {
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun storeUser(name: String, userId: String, age: String, role: String, userType: String, isOnLeave: Boolean, isDeactivated: Boolean) {
        dataStore.edit {
            it[NAME_KEY] = name
            it[USER_ID_KEY] = userId
            it[AGE_KEY] = age
            it[ROLE_KEY] = role
            it[USER_TYPE_KEY] = userType
            it[USER_ON_LEAVE_KEY] = isOnLeave
            it[USER_DEACTIVATED_KEY] = isDeactivated
        }
    }

    suspend fun saveLoginStatus(isUserLoggedIn: Boolean) {
        dataStore.edit {
            it[IS_USER_LOGGED_IN_KEY] = isUserLoggedIn
        }
    }

    val nameFlow: Flow<String> = dataStore.data.map {
        it[NAME_KEY] ?: ""
    }
    val userIdFlow: Flow<String> = dataStore.data.map {
        it[USER_ID_KEY] ?: ""
    }
    val ageFlow: Flow<String> = dataStore.data.map {
        it[AGE_KEY] ?: ""
    }
    val roleFlow: Flow<String> = dataStore.data.map {
        it[ROLE_KEY] ?: ""
    }
    val typeFlow: Flow<String> = dataStore.data.map {
        it[USER_TYPE_KEY] ?: ""
    }
    val onLeaveFlow: Flow<Boolean> = dataStore.data.map {
        it[USER_ON_LEAVE_KEY] ?: false
    }
    val deactivatedFlow: Flow<Boolean> = dataStore.data.map {
        it[USER_DEACTIVATED_KEY] ?: false
    }

    val onLoginFlow: Flow<Boolean> = dataStore.data.map {
        it[IS_USER_LOGGED_IN_KEY] ?: false
    }

}