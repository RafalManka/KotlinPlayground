buildscript {

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Plugins.gradleBuild)
        classpath(Plugins.kotlinPlugin)

    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
