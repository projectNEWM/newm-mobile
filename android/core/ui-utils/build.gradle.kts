plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
}

apply(from = "../../../gradle_include/compose.gradle")

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm.core.ui.utils"

    defaultConfig {
        minSdk = Versions.androidMinSdk
        resourcePrefix = "core_ui_utils"
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
    api(project(Modules.coreResources))
    implementation(Google.androidxCore)
    implementation(Google.material)
    implementation(Google.materialIconsExtended)
    implementation(project(Modules.coreTheme))
}
