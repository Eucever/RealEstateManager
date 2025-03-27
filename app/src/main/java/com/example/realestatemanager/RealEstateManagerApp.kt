package com.example.realestatemanager

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp


object AppSingleton {
    lateinit var applicationInstance: Application
}

@HiltAndroidApp
class RealEstateManagerApp():Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        AppSingleton.applicationInstance=this
    }
}