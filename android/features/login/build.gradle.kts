plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
    id(Plugins.parcelize)
    id(Plugins.paparazzi)
}

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm.feature.login"

    defaultConfig {
        minSdk = Versions.androidMinSdk
        resourcePrefix = "login"
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
    implementation(Circuit.foundation)
    implementation(Circuit.retained)
    implementation(Google.androidxCore)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.material)
    implementation(Google.materialIconsExtended)
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

    testImplementation(Circuit.test)
    testImplementation(Google.testParameterInjector)
    testImplementation(JUnit.jUnit)
    testImplementation(project(Modules.testUtils))

    androidTestImplementation(Google.espressoTest)
    androidTestImplementation(JUnit.androidxJUnit)
}
