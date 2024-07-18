package com.route.network.di

import android.content.Context
import coil.ImageLoader
import coil.util.DebugLogger
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.route.network.BuildConfig
import com.route.network.RouteApi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

private const val BASE_URL = BuildConfig.BASE_URL
private const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttpCallFactory(
        @ApplicationContext context: Context
    ): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    }
            )
            .cache(Cache(context.cacheDir, CACHE_SIZE))
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        json: Json,
        okHttpFactory: Lazy<Call.Factory>
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory { okHttpFactory.get().newCall(it) }
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()

    @Provides
    @Singleton
    fun provideRouteApi(
        retrofit: Retrofit
    ): RouteApi = retrofit.create(RouteApi::class.java)

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpFactory: Lazy<Call.Factory>,
    ): ImageLoader =
        ImageLoader.Builder(context)
            .callFactory(okHttpFactory.get())
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger())
                }
            }
            .crossfade(true)
            .build()



}