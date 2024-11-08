plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
//    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kspSymbolProcessing)
    id(libs.plugins.kotlinKapt.get().pluginId)
}

android {
    namespace = "com.example.crypto_info"
    compileSdk = 34

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(project(":core"))
    implementation(project(":common-ui"))
    implementation(project(":crypto-listing"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    //Hilt
//    implementation (libs.hilt.android)
//    kapt (libs.hilt.android.compiler)
//    kapt (libs.androidx.hilt.compiler)
//    implementation (libs.androidx.hilt.navigation.compose)

    //Dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    //viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    // Coil Compose
    implementation (libs.coil)

    // Accompanists swipeRefresh
    implementation(libs.accompanist.swiperefresh)

    //charts
    implementation(libs.compose.charts)
}