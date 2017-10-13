# DatabaseDemo
项目说明：学习关于 `greenDao`、`realm`、`ormlite` 、`wcdb`的学习。


## 目录结构
- [greenDao](#1.0.0)
    - [greenDao的介绍](#1.1.0)
    - [greenDao的新特性（配置、注解、加密、支持Kotlin）](#1.2.0)
    - [参考资料](#1.3.0)
- [realm](#2.0.0)
    - [realm的介绍](#2.1.0)
    - [realm的配置](#2.2.0)
    - [参考资料](#2.3.0)
- [ormlite](#3.0.0)
    - [ormlite的介绍](#3.1.0)
    - [ormlite的配置](#3.2.0)
    - [参考资料](#3.3.0)
- [wcdb](#4.0.0)
    - [wcdb的介绍](#4.1.0)
    - [wcdb的配置](#4.2.0)
    - [参考资料](#4.3.0)
- [四者间的对比](#5.0.0)


<h2 id="1.0.0"> 1、greenDao</h2>

<h3 id="#1.1.0">1.1、greenDao的介绍</h3>

- [**greendao官方文档**](http://greenrobot.org/greendao/documentation/)
- [**greendao github**](https://github.com/greenrobot/greenDAO)

`greenDAO`是一个对象`关系映射（ORM）的框架`，能够提供一个接口通过操作对象的方式去操作关系型数据库，它能够让你操作数据库时更简单、更方便。
    
<h3 id="#1.2.0">1.2、greenDao的新特性（配置、注解、加密、支持Kotlin）</h3>

#### 配置

- step1 在 project 的 gradle 文件中引入 greenDAO插件
```
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'
    }
}
```

- step2 在 module 的 gradle 文件中添加 greenDAO插件，并引入相关类库
```
apply plugin: 'org.greenrobot.greendao'  // 添加greenDao应用插件
android {
    ...
}
greendao {
    // 指定数据库schema版本号，迁移等操作会用到
    schemaVersion 1
    // dao的包名，包名默认是entity
    daoPackage 'cn.jeterlee.greendao.gen'
    // 生成数据库文件目录
    targetGenDir 'src/main/java'
}
dependencies {
    ...

    // 添加 greendao 库
    compile 'org.greenrobot:greendao:3.2.2'
    compile 'org.greenrobot:greendao-generator:3.2.2'
}
```

    说明：
    1. schemaVersion： 数据库schema版本，也可以理解为数据库版本号
    2. daoPackage：设置DaoMaster、DaoSession、Dao包名
    3. targetGenDir：设置DaoMaster、DaoSession、Dao目录
    4. targetGenDirTest：设置生成单元测试目录
    5. generateTests：设置自动生成单元测试用例

#### 注解

#### 加密

- step3 在 module 的 gradle 文件中添加引入加密类库
```
// sql加密（配合greendao数据库）
compile 'net.zetetic:android-database-sqlcipher:3.5.6'
```

<h3 id="#1.3.0">1.3、参考资料</h3>

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

---


<h2 id="2.0.0"> 2、realm</h2>

<h3 id="#2.1.0">2.1、realm的介绍</h3>

- [**realm的官网**](https://realm.io)
- [**realm github**](https://github.com/realm)
- [**realm-java库的文档**](https://realm.io/docs/java/latest/)


<h3 id="#2.2.0">2.2、realm的配置</h3>

- step1 在 project 的 gradle 文件中引入 Realm插件
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath "io.realm:realm-gradle-plugin:3.7.2" // 添加 realm 插件
    }
}
```

- step2 在 module 的 gradle 文件中添加 Realm插件，并引入相关类库
```
apply plugin: 'realm-android' // 应用realm插件
android {
    ...
}
dependencies {
    ...

    compile 'io.realm:realm-android-library:3.7.2'
}
```

<h3 id="#2.3.0">2.3、参考资料</h3>

- [Realm Java](http://blog.csdn.net/h48582291/article/details/51195577)
- [Realm使用：入门](http://www.jianshu.com/p/fdc9492b714a)

---


<h2 id="3.0.0"> 3、ormlite</h2>

<h3 id="#3.1.0">3.1、ormlite的介绍</h3>

- [**realm的官网**](https://realm.io)
- [**realm github**](https://github.com/realm)
- [**realm-java库的文档**](https://realm.io/docs/java/latest/)


<h3 id="#3.2.0">3.2、ormlite的配置</h3>

- 在 module 的 gradle 文件中引入相关ormlite类库
```
dependencies {
    ...

    // 添加 ormlite 库
    compile 'com.j256.ormlite:ormlite-core:5.0'
    compile 'com.j256.ormlite:ormlite-android:5.0'
}
```

<h3 id="#3.3.0">3.3、参考资料</h3>

---


<h2 id="4.0.0"> 4、wcdb</h2>

<h3 id="#4.1.0">4.1、wcdb的介绍</h3>

- [**wcdb github**](https://github.com/Tencent/wcdb)


<h3 id="#4.2.0">4.2、wcdb的配置</h3>

<h3 id="#4.3.0">4.3、参考资料</h3>

- [微信 WCDB for Android的接入](http://www.jianshu.com/p/d0edbbfcf988)

---


<h2 id="5.0.0"> 5、四者间的对比</h2>

---


# License

```
 Copyright (c) 2017, The Jeterlee authors 

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
