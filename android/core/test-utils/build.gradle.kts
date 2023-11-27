plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
}

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm.core.test.utils"

    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        resourcePrefix = "core_test_utils"
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
    implementation("app.cash.paparazzi:paparazzi:${Versions.paparazzi}")
    implementation(Google.composeUi)
    implementation(project(Modules.coreTheme))

    testImplementation(Circuit.test)
    testImplementation(Google.testParameterInjector)
    testImplementation(JUnit.jUnit)

    androidTestImplementation(Google.espressoTest)
    androidTestImplementation(JUnit.androidxJUnit)
}
