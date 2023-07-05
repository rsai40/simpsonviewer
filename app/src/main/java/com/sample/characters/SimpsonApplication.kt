package com.sample.characters

import android.app.Application
import androidx.multidex.MultiDex

class SimpsonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)
    }
}