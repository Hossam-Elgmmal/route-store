package com.route.datastore

import androidx.datastore.core.DataStore
import com.route.model.UserInfo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data.map { preferences ->
        com.route.model.UserData(
            darkTheme = when (preferences.darkTheme) {
                UserPreferences.DarkTheme.FOLLOW_SYSTEM -> com.route.model.DarkTheme.FOLLOW_SYSTEM
                UserPreferences.DarkTheme.LIGHT -> com.route.model.DarkTheme.LIGHT
                UserPreferences.DarkTheme.DARK -> com.route.model.DarkTheme.DARK
                else -> com.route.model.DarkTheme.FOLLOW_SYSTEM
            },
            userName = preferences.userName,
            userEmail = preferences.userEmail,
            userPassword = preferences.userPassword,
            userToken = preferences.userToken,
        )
    }

    suspend fun setDarkTheme(darkTheme: com.route.model.DarkTheme) {
        val newDarkTheme = when (darkTheme) {
            com.route.model.DarkTheme.LIGHT -> UserPreferences.DarkTheme.LIGHT
            com.route.model.DarkTheme.DARK -> UserPreferences.DarkTheme.DARK
            else -> UserPreferences.DarkTheme.FOLLOW_SYSTEM
        }
        userPreferences.updateData {
            it.toBuilder()
                .setDarkTheme(newDarkTheme)
                .build()
        }
    }

    suspend fun setUserInfo(
        userInfo: UserInfo
    ) {
        userPreferences.updateData {
            it.toBuilder()
                .setUserName(userInfo.name)
                .setUserEmail(userInfo.email)
                .setUserPassword(userInfo.password)
                .setUserToken(userInfo.token)
                .build()
        }
    }
}