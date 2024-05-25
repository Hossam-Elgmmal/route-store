package com.route.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.route.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val isDarkTheme: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[DARK_THEME] ?: false
        }

    val userData: Flow<UserData> = isDarkTheme.map {
        UserData(it)
    }

    private companion object {
        val DARK_THEME = booleanPreferencesKey("DARK_THEME")
    }

    suspend fun saveThemePreferences(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME] = isDarkTheme
        }
    }
}