plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply(from = "../../../gradle_include/compose.gradle")

android {
    namespace = "io.newm.feature.barcode.scanner"
    compileSdk = Versions.androidCompileSdk

    defaultConfig {
        minSdk = Versions.androidMinSdk
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
    implementation("androidx.camera:camera-camera2:1.4.0-alpha04")
    implementation("androidx.camera:camera-lifecycle:1.4.0-alpha04")
    implementation("androidx.camera:camera-view:1.4.0-alpha04")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")
    implementation(Google.androidxCore)
    implementation(Google.material)
    implementation(Google.materialIconsExtended)
    implementation(Google.playServicesAuth)
    implementation(project(Modules.coreResources))
    implementation(project(Modules.coreTheme))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.shared))
}