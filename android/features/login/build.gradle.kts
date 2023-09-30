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
        targetSdk = Versions.androidTargetSdk
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

    implementation(project(Modules.coreTheme))
    implementation(project(Modules.shared))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.coreResources))

    implementation(Circuit.foundation)
    implementation(Circuit.retained)

    implementation(Google.androidxCore)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.playServicesAuth)
    implementation(Google.material)
    implementation(Google.navigationCompose)
    implementation(Google.materialIconsExtended)

    implementation(Koin.android)
    implementation(Koin.androidCompose)

    debugImplementation(Google.composeUiTooling)
    debugImplementation(Google.composeUiTestManifest)

    testImplementation(project(Modules.testUtils))
    testImplementation(Circuit.test)
    testImplementation(JUnit.jUnit)
    testImplementation(Google.testParameterInjector)

    androidTestImplementation(JUnit.androidxJUnit)
    androidTestImplementation(Google.espressoTest)
}
