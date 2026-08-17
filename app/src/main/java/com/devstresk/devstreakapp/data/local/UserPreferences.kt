package com.devstresk.devstreakapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        val USER_HANDLE_KEY = stringPreferencesKey("user_handle")
    }


    val userHandle: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_HANDLE_KEY]
    }


    suspend fun saveUserHandle(handle: String?) {
        context.dataStore.edit { preferences ->
            if (handle.isNullOrBlank()) {
                preferences.remove(USER_HANDLE_KEY)
            } else {
                preferences[USER_HANDLE_KEY] = handle
            }
        }
    }
}