plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
    id(Plugins.parcelize)
    id(Plugins.paparazzi)
}

apply(from = "../../../gradle_include/compose.gradle")
apply(from = "../../../gradle_include/circuit.gradle")

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


    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(Google.androidxCore)
    implementation(Google.material)
    implementation(Google.materialIconsExtended)
    implementation(Google.playServicesAuth)
    implementation(Koin.android)
    implementation(project(Modules.coreResources))
    implementation(project(Modules.coreTheme))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.shared))

    testImplementation(Google.testParameterInjector)
    testImplementation(JUnit.jUnit)
    testImplementation(project(Modules.testUtils))

    androidTestImplementation(Google.espressoTest)
    androidTestImplementation(JUnit.androidxJUnit)
}
