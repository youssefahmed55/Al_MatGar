package com.example.e_commerce.services

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExampleApplication : Application(){

    override fun onCreate() {
        super.onCreate()
    }
}