plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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

    kotlinOptions {
        jvmTarget = "11"
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
    implementation(project(Modules.coreResources))
    implementation(project(Modules.coreTheme))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.shared))
}