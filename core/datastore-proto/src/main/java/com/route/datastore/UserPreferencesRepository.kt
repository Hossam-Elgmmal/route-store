package com.route.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.route.model.DarkTheme
import com.route.model.UserData
import com.route.model.UserInfo
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val TAG = "UserPreferencesRepository"

class UserPreferencesRepository @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data.map { preferences ->
        UserData(
            darkTheme = when (preferences.darkTheme) {
                UserPreferences.DarkTheme.FOLLOW_SYSTEM -> DarkTheme.FOLLOW_SYSTEM
                UserPreferences.DarkTheme.LIGHT -> DarkTheme.LIGHT
                UserPreferences.DarkTheme.DARK -> DarkTheme.DARK
                else -> DarkTheme.FOLLOW_SYSTEM
            },
            userName = preferences.userName,
            userEmail = preferences.userEmail,
            userPassword = preferences.userPassword,
            userToken = preferences.userToken,
        )
    }

    suspend fun setDarkTheme(darkTheme: DarkTheme) {
        val newDarkTheme = when (darkTheme) {
            DarkTheme.LIGHT -> UserPreferences.DarkTheme.LIGHT
            DarkTheme.DARK -> UserPreferences.DarkTheme.DARK
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

    suspend fun getDataVersion() = userPreferences.data
        .map {
            DataVersion(
                categoryVersion = it.categoryVersion,
                subCategoryVersion = it.subCategoryVersion,
                brandVersion = it.brandVersion,
                productVersion = it.productVersion,
            )
        }.firstOrNull() ?: DataVersion()

    suspend fun updateDataVersion(update: DataVersion.() -> DataVersion) {
        try {
            userPreferences.updateData { preferences ->

                val updatedVersions = update(
                    DataVersion(
                        categoryVersion = preferences.categoryVersion,
                        subCategoryVersion = preferences.subCategoryVersion,
                        brandVersion = preferences.brandVersion,
                        productVersion = preferences.productVersion,
                    )
                )

                preferences.toBuilder()
                    .setCategoryVersion(updatedVersions.categoryVersion)
                    .setSubCategoryVersion(updatedVersions.subCategoryVersion)
                    .setBrandVersion(updatedVersions.brandVersion)
                    .setProductVersion(updatedVersions.productVersion)
                    .build()
            }

        } catch (e: IOException) {
            Log.e(TAG, "Failed to update user preferences: ", e)
        }
    }
}