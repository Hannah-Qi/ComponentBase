package com.example.annotation.bean

import javax.lang.model.element.Element
import kotlin.reflect.KClass

/**
 * element 每个Activity都是一个元素
 * model 每个Activity的Class
 */
class RouterBean(builder: Builder) {

    private var element: Element? = null
    private var kClass: KClass<*>? = null
    private var type: RouterType? = null
    private var path:String = ""
    private var group:String = "null"


    init {
        this.element = builder.element
        this.group = builder.group
        this.path = builder.path
    }

    companion object{
        fun create(clazz:KClass<*>, type: RouterType, path: String, group: String) : RouterBean{

            val bean = Builder
                .addGroup(group)
                .addPath(path)
                .build()
            bean.setType(type)
            bean.setClass(clazz)
            return bean
        }
    }
    /**
     * 建造者模式
     */
    object Builder{

        var element: Element? = null
        var path:String = ""
        var group:String = ""

        fun addElement(element: Element):Builder{

            this.element = element
            return this
        }

        fun addGroup(group:String):Builder{

            this.group = group
            return this
        }

        fun addPath(path:String):Builder{

            this.path = path
            return this
        }

        fun build():RouterBean{
            return RouterBean(this)
        }

    }

    fun setType(type: RouterType){
        this.type = type
    }

    fun setClass(clazz:KClass<*>){
        this.kClass = clazz
    }


    fun getType():RouterType{
        return type!!
    }

    fun getClass():KClass<*>{
        return kClass!!
    }

    fun getGroup():String{
        return group
    }

    fun getPath():String{
        return path
    }

    fun getElement():Element{
        return element!!
    }
}


enum class RouterType{
    ACTIVITY,
    FRAGMENT
}