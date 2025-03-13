package com.example.annotation_processor

import com.example.annotation.Parameter
import com.example.annotation_processor.config.ACTIVITY_PACKAGE_NAME
import com.example.annotation_processor.config.A_ROUTER_PARAMETER
import com.example.annotation_processor.config.A_ROUTER_PATH
import com.example.annotation_processor.config.PARAMETER_FILE_NAME
import com.example.annotation_processor.config.PARAMETER_METHOD_NAME
import com.example.annotation_processor.config.PARAMETER_PACKAGE_NAME
import com.example.annotation_processor.config.PARAMETER_TARGET_NAME
import com.example.annotation_processor.config.ROUTER_API_PACKAGE_NAME
import com.example.annotation_processor.config.ROUTER_PACKAGE_NAME
import com.example.annotation_processor.config.note
import com.example.annotation_processor.factory.ParamenterFactory
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ANY
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
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

@AutoService(Processor::class) //编译器绑定
@SupportedAnnotationTypes(PARAMETER_PACKAGE_NAME) //表示我要处理哪个注解
@SupportedSourceVersion(SourceVersion.RELEASE_17) //支持的java版本
class ParameterProcessor : AbstractProcessor(){

    //操作Element的工具类，函数、类、属性都是Element
    private lateinit var elementsTool: Elements

    //类信息的工具
    private lateinit var typesTool: Types

    //编译期打印日志
    private lateinit var messager: Messager

    //文件生成器
    private lateinit var filer: Filer

    //key是类（MainActivity） value是属性集合
    private var parameterMap = mutableMapOf<TypeElement, MutableList<Element>>()

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        if (processingEnv == null) return

        elementsTool = processingEnv.elementUtils
        typesTool = processingEnv.typeUtils
        messager = processingEnv.messager
        filer = processingEnv.filer
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        val elementsSet = p1?.getElementsAnnotatedWith(Parameter::class.java)

        if(!elementsSet.isNullOrEmpty()) {

            //遍历注释的属性，并以KV的形式存放
            elementsSet.forEach { element ->
                //找到父节点，是一个类
                val enclosingElement = element.enclosingElement as TypeElement
                if (parameterMap.containsKey(enclosingElement)) {
                    parameterMap[enclosingElement]?.add(element)
                } else {
                    val fieldList = mutableListOf<Element>()
                    fieldList.add(element)
                    parameterMap[enclosingElement] = fieldList

                }
            }

            //生成 类文件
            if (parameterMap.isEmpty()) return false
            val activityType = elementsTool.getTypeElement(ACTIVITY_PACKAGE_NAME)

            /**
             * import com.example.componentbase.MainActivity
             * import com.example.router_api_k.ParameterLoad
             * import kotlin.Any
             *
             * public class MainActivity_Parameter : ParameterLoad {
             *   override fun getParameter(targetParameter: Any) {
             *     var t = targetParameter as MainActivity
             *     val i = t.intent
             *     t.name = i.getStringExtra("name") ?: ""
             *     t.age = i.getIntExtra("age", 0)
             *   }
             * }
             *
             * */

            parameterMap.forEach { enterySet ->
                val typeElement = enterySet.key
                val parameterList = enterySet.value

                if(!typesTool.isSubtype(typeElement.asType(), activityType.asType())) {
                    throw RuntimeException("Parameter now only support Activity")
                }

                val paramenterFactory = ParamenterFactory.Builder
                    .setTypeElement(typeElement)
                    .build()

                paramenterFactory.addFirstLineCode()

                messager.printMessage(note, "parameterList size = ${parameterList.size}")
                parameterList.forEach {
                    paramenterFactory.buildOtherLineCode(it)
                }

                val finalClassName = "${typeElement.simpleName}$PARAMETER_FILE_NAME"

                val classSpec = TypeSpec.classBuilder(finalClassName)
                    .addSuperinterface(ClassName(ROUTER_API_PACKAGE_NAME, A_ROUTER_PARAMETER))
                    .addFunction(
                        paramenterFactory.build()
                    ).build()

                //创建文件
                val fileBuilder = FileSpec.builder("", "${typeElement.simpleName}$PARAMETER_FILE_NAME")
                    .addType(classSpec).build()

                fileBuilder.writeTo(filer)
            }
        }
        return true
    }
}