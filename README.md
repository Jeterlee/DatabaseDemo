## Realm
**项目说明：关于 `realm` 数据库的使用学习。**


## Realm介绍
- [**`realm`官网**](https://realm.io)
- [**`realm github`**](https://github.com/realm)
- [**`realm-java`库文档**](https://realm.io/docs/java/latest/)


## Realm配置
- step1 在 project 的 gradle 文件中引入 `Realm` 插件
```gradle
buildscript {
    repositories {
        google()
        jcenter()
        maven {
            url 'http://oss.jfrog.org/artifactory/oss-snapshot-local'
        }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.3'
        classpath "io.realm:realm-gradle-plugin:5.4.0-SNAPSHOT" // 添加realm插件

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
```

- step2 在 module 的 gradle 文件中添加 `Realm` 插件，并引入相关类库
```gradle
apply plugin: 'realm-android' // 应用realm插件
```


## 参考资料
- [Realm Java](http://blog.csdn.net/h48582291/article/details/51195577)
- [Realm使用：入门](http://www.jianshu.com/p/fdc9492b714a)

