plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    namespace = "io.newm.core.resources"
    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }

    lint { baseline = file("lint-baseline.xml") }
}

dependencies {
    implementation(libs.androidx.material)
    implementation(libs.androidx.core.splashscreen)
}
