package com.example.youtune

import android.app.Application

class YoutuneApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        LibraryRepository.initialize(this)
    }
}