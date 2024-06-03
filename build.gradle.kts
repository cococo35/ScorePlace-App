// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    //Firebase SDK 사용을 위한 사전 작업 1: Google services Gradle plugin 디펜던시 추가.
    //참고: 현재 이 파일은 루트 수준(프로젝트 수준) Gradle 파일입니다. Hanple/build.gradle.kts
    id("com.google.gms.google-services") version "4.4.2" apply false

}
buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}