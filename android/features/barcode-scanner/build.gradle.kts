plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.multiplatform)
}

apply(from = "../../../gradle_include/compose.gradle")

android {
    namespace = "io.newm.feature.barcode.scanner"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        resourcePrefix = "barcode-scanner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.barcode.scanning)
    implementation(libs.play.services.auth)
    implementation(compose.material)
    implementation(libs.guava)
    implementation(project(Modules.CORE_RESOURCES))
    implementation(project(Modules.CORE_THEME))
    implementation(project(Modules.CORE_UI_UTILS))
    implementation(project(Modules.SHARED))
}

kotlin { compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11) } }
