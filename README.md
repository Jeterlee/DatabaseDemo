## greenDAO
**项目说明：关于 `greenDAO` 的使用学习。**

## greenDAO 介绍
- [**greenDAO 官方文档**](http://greenrobot.org/greendao/documentation/)
- [**greenDAO github**](https://github.com/greenrobot/greenDAO)

`greenDAO` 是一个对象 `关系映射（ORM）的框架`，能够提供一个接口通过操作对象的方式去操作关系型数据库，它能够让你操作数据库时更简单、更方便。


## greenDAO 新特性（配置、注解、加密、支持 Kotlin）
#### 1、配置
- step1：在 project 的 gradle 文件中引入 greenDAO 插件
```gradle
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'
    }
}
```

- step2：在 module 的 gradle 文件中添加 greenDAO 插件，并引入相关类库
```gradle
apply plugin: 'org.greenrobot.greendao'  // 添加 greenDAO 应用插件
android {
    ...
}
greendao {
    // 指定数据库 schema 版本号，迁移等操作会用到
    schemaVersion 1
    // dao 的包名，包名默认是 entity
    daoPackage 'com.github.jeterlee.greendao.gen'
    // 生成数据库文件目录
    targetGenDir 'src/main/java'
}
dependencies {
    ...

    // 添加 greenDAO 库
    implementation 'org.greenrobot:greendao:3.2.2'
    implementation 'org.greenrobot:greendao-generator:3.2.2'
}
```

    说明：
    1. schemaVersion： 数据库 schema 版本，也可以理解为数据库版本号
    2. daoPackage：设置 DaoMaster、DaoSession、Dao 包名
    3. targetGenDir：设置 DaoMaster、DaoSession、Dao 目录
    4. targetGenDirTest：设置生成单元测试目录
    5. generateTests：设置自动生成单元测试用例

#### 2、注解

#### 3、加密
- step3：在 module 的 gradle 文件中添加引入加密类库
```gradle
// sql加密（配合 greenDAO 数据库）
compile 'net.zetetic:android-database-sqlcipher:3.5.6'
```

## 参考资料
- [android greendao3 介绍 配置 加密 支持kotliln](http://blog.csdn.net/qqduxingzhe/article/details/76573075)
- [GreenDao3.0简单使用](http://www.jianshu.com/p/4986100eff90)
- [GreenDao3.x的简单使用](http://www.jianshu.com/p/c024928e6c93)
- [GreenDao 3.X之RxJava](http://blog.csdn.net/io_field/article/details/52214321)
- [玩转Android之数据库框架greenDAO3.0使用指南 ](http://blog.csdn.net/u012702547/article/details/52226163)
- [GreenDao3.0新特性解析（配置、注解、加密）](http://blog.csdn.net/huangjiamingboke/article/details/60867642)
- [GreenDao3.0使用详解](http://blog.csdn.net/u014752325/article/details/53996232)
- [Android ORM框架介绍之greenDao注解及加密(三)](http://blog.csdn.net/qq_33689414/article/details/52304819)
- [GreenDao的实用封装](http://blog.csdn.net/jamy2015/article/details/51744682)
- [封装篇——DataBase 数据库整理（greenDao示例）](http://blog.csdn.net/sinat_15877283/article/details/51098477)