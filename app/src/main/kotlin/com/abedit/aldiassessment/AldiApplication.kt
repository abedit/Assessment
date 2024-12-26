package com.abedit.aldiassessment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AldiApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}