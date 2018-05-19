object Versions {
    val gradle = "3.1.2"
    val kotlin = "1.2.41"
}

object Plugins {
    val gradleBuild = "com.android.tools.build:gradle:${Versions.gradle}"
    val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}