plugins {
    id("com.android.library")
    kotlin("android")
}

apply(from = "../../../gradle_include/circuit.gradle")
apply(from = "../../../gradle_include/compose.gradle")

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    namespace = "io.newm.core.test.utils"

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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
    implementation(libs.paparazzi)
    implementation(project(Modules.coreTheme))

    testImplementation(libs.test.parameter.injector)
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.junit)
}
