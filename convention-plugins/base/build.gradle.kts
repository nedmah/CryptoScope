plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}


dependencies {
    implementation(libs.androidToolsBuildGradle)
    implementation(libs.kotlinGradle)

    // Workaround for version catalog working inside precompiled scripts
    // Issue - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))


//    val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libs.versions.java.get())
//
//    java {
//        sourceCompatibility = projectJavaVersion
//        targetCompatibility = projectJavaVersion
//    }
//    tasks.withType<KotlinCompile>().configureEach {
//        compilerOptions.jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
//    }
}