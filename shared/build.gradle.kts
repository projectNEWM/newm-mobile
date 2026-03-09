import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.ksp)
    alias(libs.plugins.buildconfig)
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
    androidTarget { compilerOptions { jvmTarget.set(JvmTarget.JVM_11) } }
    jvm { compilerOptions { jvmTarget.set(JvmTarget.JVM_11) } }

    val xcf = XCFramework()
    listOf(iosArm64(), iosSimulatorArm64()).forEach {
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
                api(libs.ktor.client.encoding)
                implementation(libs.kotlinInject.runtime)
                implementation(libs.store5)
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutines.extensions)
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
                implementation(libs.sqldelight.android.driver)
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
                implementation(libs.sqldelight.native.driver)
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
                implementation(libs.sqldelight.sqlite.driver)
                implementation(libs.ktor.client.cio)
            }
        }

        all { languageSettings.optIn("kotlin.experimental.ExperimentalObjCName") }

        @OptIn(ExperimentalWasmDsl::class) wasmJs { browser {} }

        val wasmJsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
                implementation(libs.kotlin.browser)
                // Note: SQLDelight and DataStore don't have wasmJs support yet
                // Database and preferences are handled via browser localStorage
            }
        }
    }
}

buildConfig {
    packageName("io.newm.shared.generated")

    val props =
        Properties().apply {
            val localPropertiesFile = File(rootProject.rootDir, "local.properties")
            if (localPropertiesFile.exists()) {
                localPropertiesFile.inputStream().use { load(it) }
            }
        }

    // Helper: return empty string if key is missing to avoid build failure
    fun req(name: String): String = props.getProperty(name)?.trim('"') ?: ""

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

kotlin.sourceSets.all { languageSettings.optIn("kotlin.experimental.ExperimentalObjCName") }

dependencies {
    add("kspAndroid", libs.kotlinInject.compiler)
    add("kspJvm", libs.kotlinInject.compiler)
    add("kspWasmJs", libs.kotlinInject.compiler)
}
