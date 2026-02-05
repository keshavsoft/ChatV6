package com.example.compose.jetchat

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppWebSocketManager.connect()
    }
}