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

    implementation(project(Modules.coreTheme))
    implementation(project(Modules.shared))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.coreResources))

    implementation(Google.androidxCore)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiUtil)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.playServicesAuth)
    implementation(Google.material)
    implementation(Google.media3Exoplayer)
    implementation(Google.media3ui)
    implementation(Google.media3Session)
    implementation(Google.media3Common)
    implementation(Google.navigationCompose)
    implementation(Google.materialIconsExtended)
    implementation(Coil.compose)

    implementation(Koin.android)
    implementation(Koin.androidCompose)

    debugImplementation(Google.composeUiTooling)
    debugImplementation(Google.composeUiTestManifest)

    testImplementation(project(Modules.testUtils))
}
