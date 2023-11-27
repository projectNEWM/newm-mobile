plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
}

android {
    namespace = "io.newm.feature.musicplayer"
    compileSdk = Versions.androidCompileSdk

    defaultConfig {
        minSdk = Versions.androidMinSdk
        resourcePrefix = "musicplayer"
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
    implementation(Coil.compose)
    implementation(Google.androidxCore)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.composeUiUtil)
    implementation(Google.material)
    implementation(Google.materialIconsExtended)
    implementation(Google.media3Common)
    implementation(Google.media3Exoplayer)
    implementation(Google.media3Session)
    implementation(Google.media3ui)
    implementation(Google.navigationCompose)
    implementation(Google.playServicesAuth)
    implementation(Koin.android)
    implementation(Koin.androidCompose)
    implementation(project(Modules.coreResources))
    implementation(project(Modules.coreTheme))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.shared))

    debugImplementation(Google.composeUiTestManifest)
    debugImplementation(Google.composeUiTooling)

    testImplementation(project(Modules.testUtils))
}
