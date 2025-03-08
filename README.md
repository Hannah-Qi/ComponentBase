# ComponentBase
组件化开发

1、创建一个项目包含三个app module和一个library module


2、修改idRelese可以控制order、personal是否为一个module
3、给app、order、personal添加一个简单的UI
4、当order、personal是module时，引入app的build.gradle
5、将common引入三个module里面
6、app与order的UI是xml布局，personal的UI是Compose

7、引入order和personal的时候会将他们的Manifest文件也引入，间接的引入了他们的启动器，创建一个debug文件，copy一份Manifest文件
   如果是release的情况下走debug里面的Manifest文件，如果作为library的话，走原来的Manifest文件--->sourceSets控制

8、结合 KotlinPoet、LiveDataBus 和 ARouter，实现一个高效、解耦的 Android 组件化开发方案
KotlinPoet：用于动态生成代码，减少样板代码。
LiveDataBus：用于模块间的事件传递，支持生命周期感知。
ARouter：用于模块间的页面跳转和参数传递，支持服务调用。

9、annotationProcessor-->JavaPoet kapt-->KotlinPoet


Question：
1、项目如果是Kotlin DSL的话，无法创建app_config.gradle.kts文件，只能暂时先用Groovy DSL来创建项目？！
2、想将build出的apk文件中不需要的debug文件去除，但是 exclude '**/debug/**'不生效？！
3、代码中使用的是Groovy DSL，在build添加KotlinCompile会报错，使用JavaCompile无法解决build是messager.printMessage方法在控制台中文打印乱码问题