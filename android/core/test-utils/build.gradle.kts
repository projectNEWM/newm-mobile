plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
}

apply(from = "../../../gradle_include/circuit.gradle")
apply(from = "../../../gradle_include/compose.gradle")

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm.core.test.utils"

    defaultConfig {
        minSdk = Versions.androidMinSdk
        resourcePrefix = "core_test_utils"
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
    implementation("app.cash.paparazzi:paparazzi:${Versions.paparazzi}")
    implementation(project(Modules.coreTheme))

    testImplementation(Google.testParameterInjector)
    testImplementation(JUnit.jUnit)

    androidTestImplementation(Google.espressoTest)
    androidTestImplementation(JUnit.androidxJUnit)
}
