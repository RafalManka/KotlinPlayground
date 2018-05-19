object Versions {
    const val gradle = "3.1.2"
    const val kotlin = "1.2.41"
    const val appCompatV7 = "27.1.1"
    const val jUnit = "4.12"
}

object Plugins {
    const val gradleBuild = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appCompatV7 = "com.android.support:appcompat-v7:${Versions.appCompatV7}"
    const val jUnit = "junit:junit:${Versions.jUnit}"
}