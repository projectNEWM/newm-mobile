import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.plugin.parcelize)
    kotlin("plugin.serialization")
}

android {
    namespace = "io.newm.sharedfeatures"

    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.kotlinInject.runtime)
                implementation(libs.circuit.foundation)
                implementation(project(Modules.coreTheme))
                implementation(project(Modules.coreUiUtils))
                implementation(project(Modules.shared))
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.circuit.test)
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        androidMain {
            dependencies {
                api(libs.recaptcha)
                implementation(libs.androidx.activity.compose)
                implementation(libs.play.services.auth)
            }
        }

        targets.configureEach {
            if (platformType == KotlinPlatformType.androidJvm) {
                compilations.configureEach {
                    compileTaskProvider.configure {
                        compilerOptions {
                            freeCompilerArgs.addAll(
                                "-P",
                                "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=io.newm.sharedfeatures.parceling.CommonParcelize",
                            )
                        }
                    }
                }
            }
        }

        jvm("desktop")

        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
            outputModuleName = "sharedfeatures"
            browser {}
        }

    }
}
