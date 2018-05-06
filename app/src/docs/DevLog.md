## 20180505
+ 在res下添加layout子目录

## 20180506
+ 增加BaseActivity和ActivityCollector,以实现对Activity的统一管理
+ 增加MyApplication,实现context的统一提供

+ 增加对GreenDao的支持
[greenrobot/greenDAO](https://github.com/greenrobot/greenDAO#add-greendao-to-your-project)
```
// In your root build.gradle file:
buildscript {
    repositories {
        jcenter()
        mavenCentral() // add repository
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.1'
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2' // add plugin
    }
}

// In your app projects build.gradle file:
apply plugin: 'com.android.application'
apply plugin: 'org.greenrobot.greendao' // apply plugin

dependencies {
    implementation 'org.greenrobot:greendao:3.2.2' // add library
}
```

> In the build.gradle file of your app project:
[Modelling entities](http://greenrobot.org/greendao/documentation/modelling-entities/)
```
android {
...
}

greendao {
    schemaVersion 1
    daoPackage 'com.vivi.renew.app.entity'   // setting package for the generated files
}
```

> 扩展MyApplication,使其支持GreenDao

### 配置adb
+ 打开terminal终端
+ 进入**当前用户**的home目录
+ 创建.bash_profile文件，输入命令：touch .bash_profile
+ 打开.bash_profile文件，命令行输入open -e .bash_profile
+ 编辑.bash_profile文件

```
ANDROID_HOME=/Users/YourUsername/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

+ 保存并关闭.bash_profile文件
+ 输入并执行命令source .bash_profile

### adb使用

+ adb shell
**疑似android7.0或8.0的模拟器有问题，无法使用su切换到root状态；后下载6.0的模拟器，采用su root即可实现root**
