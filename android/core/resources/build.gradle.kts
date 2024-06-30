plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    namespace = "io.newm.core.resources"
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}