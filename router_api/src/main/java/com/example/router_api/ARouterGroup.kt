package com.example.router_api

interface ARouterGroup {

    /**
     * path -> RouterBean
     * group -> Module
     */
    fun getARouteGroup(): Map<String, ARouterPath>
}