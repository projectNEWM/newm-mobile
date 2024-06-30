plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    id("app.cash.paparazzi")
}

apply(from = "../../../gradle_include/compose.gradle")
apply(from = "../../../gradle_include/circuit.gradle")

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    namespace = "io.newm.feature.login"

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.koin.android)
    implementation(libs.play.services.auth)
    implementation(libs.recaptcha)
    implementation(project(Modules.coreResources))
    implementation(project(Modules.coreTheme))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.shared))

    testImplementation(libs.junit)
    testImplementation(libs.test.parameter.injector)
    testImplementation(project(Modules.testUtils))

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.junit)
}
