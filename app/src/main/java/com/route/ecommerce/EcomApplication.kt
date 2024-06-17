package com.route.ecommerce

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.route.work.Sync
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EcomApplication : Application(), ImageLoaderFactory {

    @Inject
    lateinit var imageLoader: dagger.Lazy<ImageLoader>

    override fun onCreate() {
        super.onCreate()

        Sync.initialize(this)
    }

    override fun newImageLoader(): ImageLoader =
        imageLoader.get()

}
