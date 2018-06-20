object Versions {
    const val gradle = "3.1.3"
    const val kotlin = "1.2.41"
    const val appCompatV7 = "27.1.1"
    const val jUnit = "4.12"
    const val recyclerView = "27.1.1"
    const val lifecycle = "1.1.1"
    const val retrofit = "2.4.0"
    const val caligraphy = "2.3.0"
    const val constraintLayout = "1.1.2"
}

object Plugins {
    const val gradleBuild = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appCompatV7 = "com.android.support:appcompat-v7:${Versions.appCompatV7}"
    const val designSupport = "com.android.support:design:${Versions.appCompatV7}"
    const val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"
    const val recyclerView = "com.android.support:recyclerview-v7:${Versions.recyclerView}"
    const val lifecycle = "android.arch.lifecycle:extensions:${Versions.lifecycle}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val calligraphy = "uk.co.chrisjenx:calligraphy:${Versions.caligraphy}"
    const val jUnit = "junit:junit:${Versions.jUnit}"
}