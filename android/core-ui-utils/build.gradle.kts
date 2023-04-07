plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
}

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm.core.ui.utils"

    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        resourcePrefix = "core_ui_utils"
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    api(project(Modules.coreResources))

    implementation(Google.androidxCore)
    implementation(Google.composeMaterial)
    implementation(Google.material)
}