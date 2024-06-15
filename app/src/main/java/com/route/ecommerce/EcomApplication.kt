package com.route.ecommerce

import android.app.Application
import com.route.work.Sync
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EcomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Sync.initialize(this)
    }

}
