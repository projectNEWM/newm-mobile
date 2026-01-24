import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "io.newm.core.ui"
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    namespace = "io.newm.core.ui"

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        resourcePrefix = "core_ui"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    androidTarget()

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(libs.coil.compose)
                implementation(libs.coil.ktor)
                implementation(libs.coil.svg)
                implementation(libs.kotlin.stdlib)

                api(project(Modules.CORE_RESOURCES))
            }
        }

        commonTest { dependencies { implementation(libs.kotlin.test) } }

        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.browser)
                implementation(libs.coil.gif)
                implementation(libs.ktor.client.android)
            }
        }

        jvm("desktop")

        @OptIn(ExperimentalWasmDsl::class) wasmJs { browser {} }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
}
