plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
//    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kspSymbolProcessing)
    id(libs.plugins.kotlinKapt.get().pluginId)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.cryptoscope"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cryptoscope"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

//hilt {
//    enableAggregatingTask = true
//}

dependencies {

    implementation(project(":common-ui"))
    implementation(project(":core"))
    implementation(project(":crypto-listing"))
    implementation(project(":crypto-info"))
    implementation(project(":wallet"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Navigation
    implementation(libs.compose.navigation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Hilt
//    implementation (libs.hilt.android)
//    kapt (libs.hilt.android.compiler)
//    kapt (libs.androidx.hilt.compiler)
//    implementation (libs.androidx.hilt.navigation.compose)

    //Dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    implementation(libs.compose.charts)
}