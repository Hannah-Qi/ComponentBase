package com.example.router_api_k

import android.app.Activity
import android.util.Log
import androidx.collection.LruCache
import com.example.config.TAG

/**
 * 参数管理器，主要用于接收参数
 * 1、查找 OrderMainActivity
 * 2、使用 OrderMainActivity
 * */
object ParameterManager {
    //LruCache 是 Android 提供的一个缓存类，基于 LRU（Least Recently Used，最近最少使用）算法，当缓存满时会优先移除最近最少使用的元素。
    private var cache: LruCache<String, ParameterLoad> = LruCache(128)
    val PARAMETER_FILE_NAME = "_Parameter"
    fun loadParameter(activity: Activity) {
        val className: String = activity.javaClass.simpleName
        Log.i(TAG, "className == $className")
        var parameterLoad = cache.get(className)
        Log.i(TAG, "parameterLoad == $parameterLoad")
        if(parameterLoad == null) {
            val aClass = Class.forName(className + PARAMETER_FILE_NAME)
            val constructor = aClass.getConstructor()
            parameterLoad = constructor.newInstance() as ParameterLoad
            cache.put(className, parameterLoad)
        }

        parameterLoad.getParameter(activity)
    }

}