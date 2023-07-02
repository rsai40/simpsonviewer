package com.sample.simpsonsviewer

import android.app.Application
import androidx.multidex.MultiDex

class SimpsonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)
    }
}