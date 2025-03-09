package com.example.annotation_processor

import com.example.annotation.ARouter
import com.example.annotation.bean.RouterBean
import com.example.annotation.bean.RouterType
import com.example.annotation_processor.config.ACTIVITY_PACKAGE_NAME
import com.example.annotation_processor.config.APT_PACKAGE_NAME
import com.example.annotation_processor.config.A_ROUTER_GROUP
import com.example.annotation_processor.config.A_ROUTER_PATH
import com.example.annotation_processor.config.GROUP_FILE_NAME
import com.example.annotation_processor.config.GROUP_METHOD_NAME
import com.example.annotation_processor.config.GROUP_VAR
import com.example.annotation_processor.config.MODULE_NAME
import com.example.annotation_processor.config.PATH_FILE_NAME
import com.example.annotation_processor.config.PATH_METHOD_NAME
import com.example.annotation_processor.config.PATH_VAR
import com.example.annotation_processor.config.ROUTER_API_PACKAGE_NAME
import com.example.annotation_processor.config.note
import com.example.annotation_processor.config.error
import com.example.router_api.ARouterPath
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MAP
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

@AutoService(Processor::class) //编译器绑定
@SupportedAnnotationTypes("com.example.annotation.ARouter") //表示我要处理哪个注解
@SupportedSourceVersion(SourceVersion.RELEASE_17) //支持的java版本
@SupportedOptions(MODULE_NAME, APT_PACKAGE_NAME)
class ARouterProcessor: AbstractProcessor() {
    //操作Element的工具类，函数、类、属性都是Element
    private lateinit var elementsTool: Elements

    //类信息的工具
    private lateinit var typesTool: Types

    //编译期打印日志
    private lateinit var messager: Messager

    //文件生成器
    private lateinit var filer: Filer

    private var moduleName: String? = null

    private var aptPackageName: String? = null

    private val pathMap = mutableMapOf<String,MutableList<RouterBean>>()
    private val groupMap = mutableMapOf<String,String>()

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        if (processingEnv == null) return

        elementsTool = processingEnv.elementUtils
        typesTool = processingEnv.typeUtils
        messager = processingEnv.messager
        filer = processingEnv.filer

        val options = processingEnv.options
        moduleName = options[MODULE_NAME].toString()
        aptPackageName = options[APT_PACKAGE_NAME].toString()

        if (moduleName == null) {
            messager.printMessage(error, "APT env error")
        } else {
            messager.printMessage(
                note, "processor initial completed......" +
                        "moduleName: $moduleName, aptPackageName: $aptPackageName")
        }
    }
    
    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        if(p0 == null || p0.size == 0) {
            messager.printMessage(note, "No classes annotated by ARouter were found")
            return false //表示注解处理器没有工作
        }

        p1?.let {

            val elementsSet = it.getElementsAnnotatedWith(ARouter::class.java)

            for (element: Element in elementsSet) {

                val className = element.simpleName.toString()
                messager.printMessage(note, "test className = $className")

                val annotation = element.getAnnotation(ARouter::class.java)

                getGroupNameFromPath(annotation.path)

                //创建RootBean
                val routerBean = RouterBean.Builder
                    .addElement(element)
//                    .addGroup(annotation.group)
//                    .addGroup(getGroupNameFromPath(annotation.path)) //注解里只写一个path参数，简化代码
                    .addGroup(moduleName ?: getGroupNameFromPath(annotation.path)) //只要注解里所写的path符合要求
                    .addPath(annotation.path).build()

                //判断是Activity上的注解还是Fragment上的
                val activityMirror = elementsTool.getTypeElement(ACTIVITY_PACKAGE_NAME).asType()
//                val fragmentMirror = elementsTool.getTypeElement(FRAGMENT_PACKAGE_NAME).asType()

                if(typesTool.isSubtype(element.asType(),activityMirror)){
                    routerBean.setType(RouterType.ACTIVITY)
                } /*else if(typesTool.isSubtype(element.asType(),fragmentMirror)) {
                    RouterBean.setType(RouterType.FRAGMENT)
                } */else{
                    //抛出异常
                    throw Exception("@ARouter注解只能在Activity或者Fragment上使用")
                }


                //根据group分组
                var pathChild: MutableList<RouterBean>? = pathMap[routerBean.getGroup()]

                if(pathChild.isNullOrEmpty()){
                    pathChild = mutableListOf()
                    //如果是空的
                    pathChild.add(routerBean)
                }else{
                    messager.printMessage(note, "test pathChild is $pathChild")
                    //如果不为空
                    pathChild.add(routerBean)
                }
                //添加到map中
                pathMap[routerBean.getGroup()] = pathChild
            }

            createPathFile()
            createGroupFile()
        }
        return true
    }

    private fun getGroupNameFromPath(path: String): String {
        return path.split("/")[0]
    }

    private fun createGroupFile() {
        /**
         * import com.example.router_api.ARouterGroup
         * import com.example.router_api.ARouterPath
         * import kotlin.String
         * import kotlin.collections.Map
         *
         * public class ARouteGrouporder : ARouterGroup {
         *   override fun getARouteGroup(): Map<String, ARouterPath> {
         *     val groupMap = mutableMapOf<String,ARouterPath>()
         *     groupMap["order"] = ARouter_path_order()
         *     return groupMap
         *   }
         * }
         *
         */

        val builder = FunSpec.builder(GROUP_METHOD_NAME)
            .addModifiers(KModifier.OVERRIDE)
            .returns(MAP.parameterizedBy(STRING, ARouterPath::class.asTypeName()))
            .addStatement(
                "val %N = mutableMapOf<%T,%T>()",
                GROUP_VAR,
                STRING,
                ARouterPath::class
            )

        var groupClassName = ""
        groupMap.forEach { (option, value) ->
            builder.addStatement(
                "groupMap[%S] = %T()",
                option,
                ClassName(aptPackageName ?: "",value)
            )

            groupClassName = "$GROUP_FILE_NAME$option"
        }

        builder.addStatement("return %N",GROUP_VAR)

        val classBuilder = TypeSpec.classBuilder(groupClassName)
            .addFunction(builder.build())
            .addSuperinterface(ClassName(ROUTER_API_PACKAGE_NAME, A_ROUTER_GROUP))
            .build()

        val file = FileSpec.builder(aptPackageName ?: "", groupClassName)
            .addType(classBuilder).build()

        file.writeTo(filer)
    }

    private fun createPathFile() {
        /**
         * import com.example.`annotation`.bean.RouterBean
         * import com.example.`annotation`.bean.RouterType
         * import com.example.order.OrderMainActivity
         * import com.example.router_api.ARouterPath
         * import customrouter_apt.ARouter_Path_personal
         * import kotlin.String
         * import kotlin.collections.Map
         *
         * public class ARouter_path_order : ARouterPath {
         *   override fun getARoutePath(): Map<String, RouterBean> {
         *     val pathMap = mutableMapOf<String,RouterBean>()
         *     pathMap.put("order/OrderMainActivity",RouterBean.create(OrderMainActivity::class,RouterType.ACTIVITY,"order/OrderMainActivity","order"))
         *     return pathMap
         *   }
         * }
         *
         * */
        //从pathMap中，获取path
        val builder = FunSpec.builder(PATH_METHOD_NAME)
            .addModifiers(KModifier.OVERRIDE)
            //返回值
            .returns(MAP.parameterizedBy(STRING, RouterBean::class.asTypeName()))
            .addStatement("val %N = mutableMapOf<%T,%T>()", PATH_VAR, STRING, RouterBean::class)

        var className = ""
        //map添加需要放循环里
        pathMap.forEach { (option, mutableList) ->

            //判断模块传过来的参数
            mutableList.forEach { routerBean ->

                builder.addStatement("%N.put(%S,%T.create(%T::class,%T.%L,%S,%S))",
                    PATH_VAR,
                    routerBean.getPath(),
                    routerBean::class,
                    routerBean.getElement().asType(),
                    RouterType::class,
                    routerBean.getType(),
                    routerBean.getPath(),
                    routerBean.getGroup()
                )
            }

            className = "$PATH_FILE_NAME$option"

            //往groupMap里添加
            if(!groupMap.containsKey(option)){

                groupMap[option] = className
            }
        }
        builder.addStatement("return %N",PATH_VAR)

        //创建文件
        val fileBuilder = FileSpec.builder(aptPackageName ?: "", className)
            .addType(
                TypeSpec.classBuilder(className)
                    .addFunction(builder.build())
                    .addSuperinterface(ClassName(ROUTER_API_PACKAGE_NAME, A_ROUTER_PATH))
                    .build()).build()

        fileBuilder.writeTo(filer)

        messager.printMessage(note,"groupMap $groupMap")
    }
}