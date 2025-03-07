package com.example.common

import android.app.Application
import android.util.Log
import com.example.common.config.BaseConfig

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i(BaseConfig.TAG, "common/BaseApplication onCreate:")
    }
}