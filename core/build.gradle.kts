import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kspSymbolProcessing)
    id(libs.plugins.kotlinKapt.get().pluginId)
    id(libs.plugins.parcelize.get().pluginId)
    id("android.base.config")
}

android {
    namespace = "com.example.core"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")

        val localProperties = Properties()
        localProperties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("API_KEY")}\"")
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    // Room
    implementation (libs.androidx.room.ktx)
    kapt (libs.androidx.room.compiler)
    implementation (libs.androidx.room.paging)

    //DataStore preferences
    implementation(libs.androidx.datastore.preferences)

    //Navigation
    implementation(libs.compose.navigation)
    implementation(libs.kotlinx.serialization.json)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    //Customtabs
    implementation (libs.androidx.browser)
}