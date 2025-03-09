package com.example.annotation_processor.config

import javax.tools.Diagnostic

val note = Diagnostic.Kind.NOTE
val error = Diagnostic.Kind.ERROR

const val MODULE_NAME = "moduleName"

const val APT_PACKAGE_NAME = "packageForAPT"

const val ACTIVITY_PACKAGE_NAME = "android.app.Activity"

const val FRAGMENT_PACKAGE_NAME = "androidx.fragment.app.Fragment"

const val ROUTER_PACKAGE_NAME = "com.example.annotation.ARouter"

const val ROUTER_API_PACKAGE_NAME = "com.example.router_api"

const val A_ROUTER_PATH = "ARouterPath"

const val A_ROUTER_GROUP = "ARouterGroup"

const val PATH_METHOD_NAME = "getARoutePath"

const val GROUP_METHOD_NAME = "getARouteGroup"

const val PATH_VAR = "pathMap"

const val GROUP_VAR = "groupMap"

const val PATH_FILE_NAME = "ARouter_Path_"

const val GROUP_FILE_NAME = "ARouterGroup"