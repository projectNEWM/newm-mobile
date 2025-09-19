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
    id("com.squareup.sqldelight")
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
                implementation(libs.runtime)
                implementation(libs.coroutines.extensions)
                api(libs.koin.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.auth)
                implementation(libs.kotlinInject.runtime)
                implementation(libs.androidx.datastore.preferences)
                implementation(libs.store5)
                implementation(libs.kvault)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.koin.test)
                implementation(libs.kotlinx.coroutines.test)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.android.driver)
                implementation(libs.ktor.client.android)
                implementation(libs.cloudinary.android)
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
                implementation(libs.native.driver)
            }
        }
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosSimulatorArm64Test.dependsOn(this)
        }

        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }

        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
            outputModuleName = "shared"
            browser {}
        }
    }
}

buildConfig {
    packageName("io.newm.shared.generated")

    val props = Properties().apply {
        load(FileInputStream(File(rootProject.rootDir, "local.properties")))
    }

    // Helper: fail fast if a required key is missing
    fun req(name: String): String =
        props.getProperty(name)?.trim('"') ?: error("Missing '$name' in local.properties")

    buildConfigField<String>("STAGING_URL", req("STAGING_URL"))
    buildConfigField<String>("PRODUCTION_URL", req("PRODUCTION_URL"))
    buildConfigField<String>("GOOGLE_AUTH_CLIENT_ID", req("GOOGLE_AUTH_CLIENT_ID"))
    buildConfigField<String>("RECAPTCHA_SITE_KEY", req("RECAPTCHA_SITE_KEY"))
    buildConfigField<String>("SENTRY_AUTH_TOKEN", req("SENTRY_AUTH_TOKEN"))
    buildConfigField<String>("ANDROID_SENTRY_DSN", req("ANDROID_SENTRY_DSN"))
    buildConfigField<String>("LAUNCHDARKLY_MOBILE_KEY", req("LAUNCHDARKLY_MOBILE_KEY"))
}


sqldelight {
    database("NewmDatabase") {
        packageName = "io.newm.shared.db.cache"
        sourceFolders = listOf("sqldelight")
        version = 4
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
