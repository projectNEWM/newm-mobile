plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    namespace = "io.newm.core.android.framework.implementations"
    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(project(Modules.SHARED))
}
