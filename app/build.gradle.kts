plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kspSymbolProcessing)
    alias(libs.plugins.kotlin.serialization)
    id(libs.plugins.kotlinKapt.get().pluginId)

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
            buildConfigField("String", "BUILD_TIME", "\"${System.currentTimeMillis()}\"")
        }
        debug {
            buildConfigField("String", "BUILD_TIME", "\"${System.currentTimeMillis()}\"")
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
        buildConfig = true
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



dependencies {

    implementation(project(":common-ui"))
    implementation(project(":crypto-listing"))
    implementation(project(":crypto-info"))
    implementation(project(":wallet"))
    implementation(project(":news"))
    implementation(project(":settings"))

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
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //Dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    //Work manager
    implementation (libs.androidx.work.runtime.ktx)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    implementation(libs.compose.charts)
}



afterEvaluate {

    tasks.findByName("assembleDebug")?.doLast {
        copy {
            from(file("$projectDir/build/outputs/apk/debug/app-debug.apk"))
            into(file("$rootDir"))
            rename("app-debug.apk", "CryptoScope-debug.apk")
        }
    }

    tasks.findByName("assembleRelease")?.doLast {
        copy {
            from(file("$projectDir/build/outputs/apk/release/app-release.apk"))
            into(file("$rootDir"))
            rename("app-release.apk", "CryptoScope-release.apk")
        }
    }

}