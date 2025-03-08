package com.example.annotation_processor

import com.example.annotation.ARouter
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

/**
 * 注解处理器
 */
@AutoService(Processor::class) //编译器绑定
@SupportedAnnotationTypes("com.example.annotation.ARouter") //表示我要处理哪个注解
@SupportedSourceVersion(SourceVersion.RELEASE_17) //支持的java版本
class ARouterProcessor: AbstractProcessor() {

    //操作Element的工具类，函数、类、属性都是Element
    private lateinit var elementsTool: Elements

    //类信息的工具
    private lateinit var typesTool: Types

    //编译期打印日志
    private lateinit var messager: Messager

    //文件生成器
    private lateinit var filer: Filer

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        if (processingEnv == null) return

        elementsTool = processingEnv.elementUtils
        typesTool = processingEnv.typeUtils
        messager = processingEnv.messager
        filer = processingEnv.filer

        messager.printMessage(note, "processor initial completed.....")
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        val annotations = p0
        val roundEnv = p1

        if(annotations == null || annotations.size == 0) {
            messager.printMessage(note, "No classes annotated by ARouter were found")
            return false //表示注解处理器没有工作
        }

        //获取被ARouter注解的类信息
        val elements: MutableSet<out Element>? = roundEnv?.getElementsAnnotatedWith(ARouter::class.java)
        elements?.forEach { element ->
            //获取类节点、获取包节点
            val packageElement = elementsTool.getPackageOf(element)
            val packageName = packageElement.simpleName.toString()
            messager.printMessage(note, "packageName = $packageName")

            //获取全类名
            val className = element.simpleName.toString()
            messager.printMessage(note, "There are classes annotated by ARouter： $className")

            val aRouter: ARouter = element.getAnnotation(ARouter::class.java)

            //生成一段代码
            generateHelloWorld()
        }

        return true
    }

    private fun generateHelloWorld() {
        val greeterClass = ClassName("", "Greeter")
        val file = FileSpec.builder("", "HelloWorld")
            .addType(
                TypeSpec.classBuilder("Greeter")
                    .primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter("name", String::class)
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("name", String::class)
                            .initializer("name")
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("greet")
                            .addStatement("println(%P)", "Hello, \$name")
                            .build()
                    )
                    .build()
            )
            .addFunction(
                FunSpec.builder("main")
                    .addParameter("args", String::class, KModifier.VARARG)
                    .addStatement("%T(args[0]).greet()", greeterClass)
                    .build()
            )
            .build()

        try {
            messager.printMessage(note, "filer path = ${filer}")
            file.writeTo(filer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}