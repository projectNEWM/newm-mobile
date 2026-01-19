import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("kotlinx-serialization")
    id("com.android.library")
    id("app.cash.sqldelight") version "2.1.0"
    alias(libs.plugins.ksp)
    id("com.github.gmazzo.buildconfig") version "5.6.8"
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    namespace = "io.newm.shared"

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
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    val xcf = XCFramework()
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            xcf.add(this)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlin.stdlib)
                api(libs.koin.core)
                api(libs.ktor.client.logging)
                api(libs.ktor.client.core)
                api(libs.ktor.client.cio)
                api(libs.ktor.client.content.negotiation)
                api(libs.ktor.serialization.kotlinx.json)
                api(libs.ktor.client.auth)
                implementation(libs.kotlinInject.runtime)
                implementation(libs.store5)
                implementation("app.cash.sqldelight:runtime:${libs.versions.runtime.get()}")
                implementation("app.cash.sqldelight:coroutines-extensions:${libs.versions.runtime.get()}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.koin.test)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:android-driver:${libs.versions.runtime.get()}")
                implementation(libs.ktor.client.android)
                implementation(libs.cloudinary.android)
                implementation(libs.androidx.datastore.preferences)
            }
        }

        named("androidUnitTest") {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.junit)
            }
        }

        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation("app.cash.sqldelight:native-driver:${libs.versions.runtime.get()}")
                implementation(libs.kvault)
            }
        }
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosSimulatorArm64Test.dependsOn(this)
        }

        val jvmMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:sqlite-driver:${libs.versions.runtime.get()}")
                implementation(libs.ktor.client.cio)
            }
        }

        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }

        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
            browser {}
        }

        val wasmJsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:${libs.versions.ktor.get()}")
                implementation("org.jetbrains.kotlinx:kotlinx-browser:0.2")
                // Note: SQLDelight and DataStore don't have wasmJs support yet
                // Database and preferences are handled via browser localStorage
            }
        }
    }
}

buildConfig {
    packageName("io.newm.shared.generated")

    val props = Properties().apply {
        val localPropertiesFile = File(rootProject.rootDir, "local.properties")
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use { load(it) }
        }
    }

    // Helper: return empty string if key is missing to avoid build failure
    fun req(name: String): String =
        props.getProperty(name)?.trim('"') ?: ""

    buildConfigField<String>("STAGING_URL", req("STAGING_URL"))
    buildConfigField<String>("PRODUCTION_URL", req("PRODUCTION_URL"))
    buildConfigField<String>("GOOGLE_AUTH_CLIENT_ID", req("GOOGLE_AUTH_CLIENT_ID"))
    buildConfigField<String>("RECAPTCHA_SITE_KEY", req("RECAPTCHA_SITE_KEY"))
    buildConfigField<String>("SENTRY_AUTH_TOKEN", req("SENTRY_AUTH_TOKEN"))
    buildConfigField<String>("ANDROID_SENTRY_DSN", req("ANDROID_SENTRY_DSN"))
    buildConfigField<String>("LAUNCHDARKLY_MOBILE_KEY", req("LAUNCHDARKLY_MOBILE_KEY"))
    buildConfigField<Boolean>("IS_DEBUG", req("IS_DEBUG").toBoolean())
}


sqldelight {
    databases {
        create("NewmDatabase") {
            packageName.set("io.newm.shared.db.cache")
            // srcDirs defaults to "sqldelight" relative to source sets
            // Exclude wasmJs from SQLDelight code generation since it's not supported
            // This means database functionality won't be available for wasmJs
        }
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}

dependencies {
    add("kspAndroid", libs.kotlinInject.compiler)
    add("kspJvm", libs.kotlinInject.compiler)
    add("kspWasmJs", libs.kotlinInject.compiler)
}