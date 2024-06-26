plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.scoreplace.hanple"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    secrets {
        // Optionally specify a different file name containing your secrets.
        // The plugin defaults to "local.properties"
        propertiesFileName = "secrets.properties"

        // A properties file containing default secret values. This file can be
        // checked in version control.
        defaultPropertiesFileName = "local.defaults.properties"

        // Configure which keys should be ignored by the plugin by providing regular expressions.
        // "sdk.dir" is ignored by default.
        ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
        ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
    }

    defaultConfig {
        applicationId = "com.scoreplace.hanple"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.room.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.fragment.ktx)
    //retrofit etc..
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.android.gms:play-services-maps:17.0.0")
    implementation("com.google.android.gms:play-services-location:17.0.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("io.coil-kt:coil:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")

    //Preferences DataStore 추가하기. https://developer.android.com/topic/libraries/architecture/datastore
    val dataStoreVersion = "1.1.1"
    implementation("androidx.datastore:datastore-preferences:${dataStoreVersion}")
    implementation("androidx.datastore:datastore-preferences-core:${dataStoreVersion}")

    //DataStore로 마이그레이션 전, SharedPreferences 사용을 위한 디펜던시 추가
    val core_version = "1.13.1"
    implementation("androidx.core:core-ktx:${core_version}}")

    // google place api dependency
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.0"))
    implementation("com.google.android.libraries.places:places:3.5.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")


    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.0-rc01")

    //google map sdk
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    //glide
    implementation("com.github.bumptech.glide:glide:4.14.2")

    //navigation drawer
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("com.google.android.material:material:1.12.0")

    //gif drawable로 가져오기
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")

    //room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.1")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")



    val penfeizhou_version = "3.0.1"
    implementation("com.github.penfeizhou.android.animation:glide-plugin:${penfeizhou_version}")
    implementation("com.github.penfeizhou.android.animation:apng:${penfeizhou_version}")
    //glide에 apng 확장자 지원하도록 하기. https://github.com/penfeizhou/APNG4Android
}
