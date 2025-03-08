package com.example.annotation

@Target(AnnotationTarget.CLASS) //作用在类上
@Retention(AnnotationRetention.SOURCE) //编译时有效
annotation class ARouter(
    val path: String,
    val group: String = "" //app\order\personal
)
