plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
}

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm.feature.now.playing"

    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        resourcePrefix = "now_playing"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
//    kapt(Airbnb.showkaseProcessor)

    implementation(project(Modules.coreTheme))
    implementation(project(Modules.shared))
    implementation(project(Modules.coreUiUtils))

//    implementation(Airbnb.showkase)
    implementation(Google.androidxCore)
    implementation(Google.appCompat)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.constraintLayout)
    implementation(Koin.android)
    implementation(Koin.androidCompose)
    implementation(Google.media3Exoplayer)
    implementation(Google.media3ui)
    implementation(Google.media3Common)
    implementation(Coil.compose)
    implementation(Google.material)
    implementation(Google.navigationCompose)

    debugImplementation(Google.composeUiTooling)
    debugImplementation(Google.composeUiTestManifest)

    testImplementation(JUnit.jUnit)

    androidTestImplementation(JUnit.androidxJUnit)
    androidTestImplementation(Google.espressoTest)
}