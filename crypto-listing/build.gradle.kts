plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kspSymbolProcessing)
    id(libs.plugins.kotlinKapt.get().pluginId)
    id(libs.plugins.parcelize.get().pluginId)
    id("android.base.config")
}


android {
    namespace = "com.example.cryptolisting"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":common-ui"))

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

    // Room
    implementation (libs.androidx.room.ktx)
    kapt (libs.androidx.room.compiler)
    implementation (libs.androidx.room.paging)

    //Paging
    implementation(libs.androidx.paging.compose)

    // Coil Compose
    implementation (libs.coil)

    // Accompanists swipeRefresh
    implementation(libs.accompanist.swiperefresh)

    //charts
    implementation(libs.compose.charts)

    //gson
    implementation (libs.gson)


}