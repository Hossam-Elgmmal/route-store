package com.route.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREF = "user_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREF
)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.dataStore
    }
}