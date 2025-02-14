plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("android.base.config")
}

android {
    namespace = "com.example.common_ui"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(project(":core"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    coreLibraryDesugaring(libs.desugar)
    implementation(libs.compose.charts)

    //Navigation
    implementation(libs.compose.navigation)

    // Coil Compose
    implementation (libs.coil)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}