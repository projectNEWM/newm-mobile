plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
}

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm.core.resources"
    defaultConfig {
        minSdk = Versions.androidMinSdk
    }
}