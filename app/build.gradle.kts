plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.android.hanple"
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
        applicationId = "com.android.hanple"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.fragment.ktx)
    //retrofit etc..
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.google.android.gms:play-services-maps:17.0.0")
    implementation ("com.google.android.gms:play-services-location:17.0.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("io.coil-kt:coil:2.6.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")

    //Preferences DataStore 추가하기. https://developer.android.com/topic/libraries/architecture/datastore
    val dataStoreVersion = "1.1.1"
    implementation("androidx.datastore:datastore-preferences:${dataStoreVersion}")
    implementation("androidx.datastore:datastore-preferences-core:${dataStoreVersion}")

    //DataStore로 마이그레이션 전, SharedPreferences 사용을 위한 디펜던시 추가
    val core_version = "1.13.1"
    implementation ("androidx.core:core-ktx:${core_version}}")

    // google place api dependency
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.0"))
    implementation("com.google.android.libraries.places:places:3.5.0")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")


    // Splash Screen
    implementation ("androidx.core:core-splashscreen:1.0.0-rc01")

    //google map sdk
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.14.2")

}