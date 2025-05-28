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
    id("com.github.gmazzo.buildconfig") version "5.6.5"
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
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    jvm {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
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
    }
}

buildConfig {
    packageName("io.newm.shared.generated")

    val properties = Properties().apply {
        load(FileInputStream(File(rootProject.rootDir, "local.properties")))
    }

    buildConfigField(
        type = "String",
        name = "STAGING_URL",
        expression = properties.getProperty("STAGING_URL")
    )
    buildConfigField(
        type = "String",
        name = "PRODUCTION_URL",
        properties.getProperty("PRODUCTION_URL")
    )
    buildConfigField(
        type = "String",
        name = "GOOGLE_AUTH_CLIENT_ID",
        properties.getProperty("GOOGLE_AUTH_CLIENT_ID")
    )
    buildConfigField(
        type = "String",
        name = "RECAPTCHA_SITE_KEY",
        properties.getProperty("RECAPTCHA_SITE_KEY")
    )
    buildConfigField(
        type = "String",
        name = "SENTRY_AUTH_TOKEN",
        properties.getProperty("SENTRY_AUTH_TOKEN")
    )
    buildConfigField(
        type = "String",
        name = "ANDROID_SENTRY_DSN",
        properties.getProperty("ANDROID_SENTRY_DSN")
    )
    buildConfigField(
        type = "String",
        name = "LAUNCHDARKLY_MOBILE_KEY",
        properties.getProperty("LAUNCHDARKLY_MOBILE_KEY")
    )
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
