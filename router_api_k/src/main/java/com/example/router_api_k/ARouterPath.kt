package com.example.router_api_k

import com.example.annotation.bean.RouterBean


interface ARouterPath {

    /**
     * key-path  value-RouterBean
     */
    fun getARoutePath(): Map<String, RouterBean>
}