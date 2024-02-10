plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
}

apply(from = "../../../gradle_include/compose.gradle")

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

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(Google.androidxCore)
    implementation(Google.material)
    implementation(Google.materialIconsExtended)
    implementation(Google.media3Common)
    implementation(Google.media3Exoplayer)
    implementation(Google.media3Session)
    implementation(Google.media3ui)
    implementation(Google.playServicesAuth)
    implementation(Koin.android)
    implementation(project(Modules.coreResources))
    implementation(project(Modules.coreTheme))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.shared))

    testImplementation(project(Modules.testUtils))
}
