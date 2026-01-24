import org.jetbrains.kotlin.gradle.dsl.JvmTarget

apply(from = "../gradle_include/compose.gradle")

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "io.newm.core.resources"
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure { compilerOptions { jvmTarget.set(JvmTarget.JVM_11) } }
        }
    }
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    wasmJs { browser() }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.components.resources)
            }
        }
        val androidMain by getting { dependencies {} }

    }
}

android {
    namespace = "io.newm.core.resources"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
}
