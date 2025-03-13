package com.example.router_api_k

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.collection.LruCache
import com.example.annotation.bean.RouterType
import com.example.config.TAG

object RouterManager {
    val FILE_GROUP_NAME = "ARouterGroup"
    val APT_PACKAGE_NAME = "customrouter_apt."

    var group: String? = null
    var path: String? = null
    var pathLruCache: LruCache<String, ARouterPath> = LruCache(128)
    var groupLruCache: LruCache<String, ARouterGroup> = LruCache(128)

    fun navigation(context: Context, bundleManager: BundleManager) {
        val groupClassName = "$APT_PACKAGE_NAME$FILE_GROUP_NAME$group"
        Log.i(TAG, "navigation: groupClassName = $groupClassName")
        var routerGroup = group?.let { groupLruCache.get(it) }

        if(routerGroup == null) {
            val aClass = Class.forName(groupClassName)
            val constructor = aClass.getConstructor()
            routerGroup = constructor.newInstance() as ARouterGroup
            Log.i(TAG, "navigation: routerGroup = $routerGroup")
            if (group != null) {
                groupLruCache.put(group!!, routerGroup)
            }
        }

        var routerPath = path?.let { pathLruCache.get(it) }
        if(routerPath == null) {
            val aClass = routerGroup.getARouteGroup()[group]
            Log.i(TAG, "navigation: aClass = $aClass")
            if (path != null) {
                if (aClass != null) {
                    pathLruCache.put(path!!,aClass)
                }
            }
        }


        routerPath = path?.let { pathLruCache.get(it) }
        val routerBean = routerPath?.getARoutePath()?.get(path)
        Log.i(TAG, "navigation: routerBean = $routerBean")
        routerBean?.let {
            when(it.getType()) {
                RouterType.ACTIVITY -> {
                    val intent = Intent(context, it.getClass().java)
                    intent.putExtras(bundleManager.getBundle())
                    context.startActivity(intent, bundleManager.getBundle())
                }
                else -> {
                    Log.i(TAG, "暂时不处理其他类型跳转")
                }
            }
        }
    }

    fun build(path: String): BundleManager {
        val groupString = path.subSequence(0, path.indexOf("/")).toString()
        this.path = path
        this.group = groupString
        return BundleManager()
    }
}