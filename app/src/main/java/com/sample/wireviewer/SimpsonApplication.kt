package com.sample.wireviewer

import android.app.Application
import androidx.multidex.MultiDex

class SimpsonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)
    }
}