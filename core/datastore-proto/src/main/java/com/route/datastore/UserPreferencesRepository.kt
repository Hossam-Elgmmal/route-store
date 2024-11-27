package com.route.datastore

import android.util.Log
import androidx.datastore.core.DataStore
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
            userImgPath = preferences.userImgName,
            userPhone = preferences.userPhone
        )
    }

    suspend fun setDarkTheme(selectedDarkTheme: DarkTheme) {
        val newDarkTheme = when (selectedDarkTheme) {
            DarkTheme.LIGHT -> UserPreferences.DarkTheme.LIGHT
            DarkTheme.DARK -> UserPreferences.DarkTheme.DARK
            else -> UserPreferences.DarkTheme.FOLLOW_SYSTEM
        }
        try {
            userPreferences.updateData { preferences ->
                preferences.copy {
                    darkTheme = newDarkTheme
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update user preferences: ", e)
        }
    }

    suspend fun setUserInfo(
        userInfo: UserInfo
    ) {
        try {
            userPreferences.updateData { preferences ->
                preferences.copy {
                    userName = userInfo.name
                    userEmail = userInfo.email
                    userPassword = userInfo.password
                    userToken = userInfo.token
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update user preferences: ", e)
        }
    }

    suspend fun getDataVersion() = userPreferences.data
        .map {
            DataVersion(
                categoryVersion = it.categoryVersion,
                subCategoryVersion = it.subCategoryVersion,
                brandVersion = it.brandVersion,
                productVersion = it.productVersion,
                orderVersion = it.orderVersion,
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
                        orderVersion = preferences.orderVersion
                    )
                )

                preferences.copy {
                    categoryVersion = updatedVersions.categoryVersion
                    subCategoryVersion = updatedVersions.subCategoryVersion
                    brandVersion = updatedVersions.brandVersion
                    productVersion = updatedVersions.productVersion
                    orderVersion = updatedVersions.orderVersion
                }
            }

        } catch (e: IOException) {
            Log.e(TAG, "Failed to update user preferences: ", e)
        }
    }

    suspend fun setToken(token: String) {
        try {
            userPreferences.updateData { preferences ->
                preferences.copy {
                    userToken = token
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update user preferences: ", e)
        }
    }

    suspend fun getToken() = userPreferences.data
        .map { preferences ->
            preferences.userToken
        }.firstOrNull() ?: ""

    suspend fun setUserId(newUserId: String) {
        try {
            userPreferences.updateData { preferences ->
                preferences.copy {
                    userId = newUserId
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update user preferences: ", e)
        }
    }

    suspend fun getUserId() = userPreferences.data
        .map { preferences ->
            preferences.userId
        }.firstOrNull() ?: ""

    suspend fun setUserImgName(fileName: String) {
        try {
            userPreferences.updateData { preferences ->
                preferences.copy {
                    userImgName = fileName
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update user preferences: ", e)
        }
    }

    suspend fun updateUserInfo(name: String, email: String, phone: String) {
        try {
            userPreferences.updateData { preferences ->
                preferences.copy {
                    userName = name
                    userEmail = email
                    userPhone = phone
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update user preferences: ", e)
        }
    }

    suspend fun updateUserPassword(newToken: String, password: String) {
        try {
            userPreferences.updateData { preferences ->
                preferences.copy {
                    userToken = newToken
                    userPassword = password
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to update user preferences: ", e)
        }
    }
}