plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
    }
}

dependencies {
    implementation(Google.appCompat)
    implementation(Google.composeMaterial)
}