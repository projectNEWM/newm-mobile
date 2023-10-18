plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(Modules.coreTheme))
    implementation(project(Modules.shared))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.coreResources))

    implementation(Google.androidxCore)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.playServicesAuth)
    implementation(Google.material)
    implementation(Google.navigationCompose)
    implementation(Google.materialIconsExtended)

    implementation("com.google.mlkit:barcode-scanning:17.2.0")
    implementation("androidx.camera:camera-camera2:1.4.0-alpha01")
    implementation("androidx.camera:camera-lifecycle:1.4.0-alpha01")
    implementation("androidx.camera:camera-view:1.4.0-alpha01")
}