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


Question：
1、项目如果是Kotlin DSL的话，没法创建app_config.gradle.kts文件，只能暂时先用Groovy DSL来创建项目？！
2、想将build出的apk文件中不需要的debug文件去除，但是 exclude '**/debug/**'不生效？！