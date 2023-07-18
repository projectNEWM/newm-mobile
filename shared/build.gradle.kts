import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin(Plugins.multiplatform)
    kotlin(Plugins.serialization)
    id(Plugins.kotlinxSerialization)
    id(Plugins.androidLibrary)
    id(Plugins.sqlDelight)
    id("com.google.devtools.ksp") version "1.8.20-1.0.11"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-7"

}

android {
    compileSdk = Versions.androidCompileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    namespace = "io.newm.shared"
    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies {
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha03")
}

kotlin {
    android()

    val xcf = XCFramework()
    listOf(
        iosX64(),
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
                implementation(Kotlin.coroutinesCore)
                implementation(SqlDelight.runtime)
                api(Koin.core)
                api(Log.kermit)
                implementation(Ktor.clientLogging)
                implementation(Ktor.ktorClientCore)
                implementation(Ktor.ktorClientCIO)
                implementation(Ktor.clientContentNegotiation)
                implementation(Ktor.kotlinXJson)
                implementation(Ktor.clientAuth)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Koin.test)
                implementation(Kotlin.coroutinesTest)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(SqlDelight.androidDriver)
                implementation(Ktor.clientAndroid)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Ktor.iosDarwin)
                implementation(SqlDelight.nativeDriver)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

sqldelight {
    database("NewmDatabase") {
        packageName = "io.newm.shared.db.cache"
//        sourceFolders = listOf("sqldelight")
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}