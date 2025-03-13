package com.example.router_api_k

interface ARouterGroup {

    /**
     * path -> RouterBean
     * group -> Module
     */
    fun getARouteGroup(): Map<String, ARouterPath>
}