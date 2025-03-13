package com.example.annotation

@Target(AnnotationTarget.FIELD) //作用在属性上
@Retention(AnnotationRetention.SOURCE) //编译时有效
annotation class Parameter(
    val name: String = "" //如果不填写变量名就是key
)
