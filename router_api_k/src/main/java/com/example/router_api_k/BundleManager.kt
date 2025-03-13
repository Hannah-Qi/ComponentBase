package com.example.router_api_k

import android.content.Context
import android.os.Bundle

class BundleManager {
    private var bundle = Bundle()

    fun getBundle(): Bundle {
        return bundle
    }

    fun withString(key: String, value: String): BundleManager {
        bundle.putString(key, value)
        return this
    }

    fun withBoolean(key: String, value: Boolean): BundleManager {
        bundle.putBoolean(key, value)
        return this
    }

    fun withInt(key: String, value: Int): BundleManager {
        bundle.putInt(key, value)
        return this
    }

    fun withBundle(bundle: Bundle): BundleManager {
        this.bundle = bundle
        return this
    }


    fun navigation(context: Context): Any {
        return RouterManager.navigation(context, this)
    }
}