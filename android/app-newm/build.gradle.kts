import java.text.SimpleDateFormat
import java.util.Date

apply(from = "../../gradle_include/compose.gradle")
apply(from = "../../gradle_include/circuit.gradle")
apply(from = "../../gradle_include/flipper.gradle")

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")

    id("io.sentry.android.gradle") version "4.9.0"
}


android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    namespace = "io.newm"
    testNamespace = "io.newm.test"
    defaultConfig {
        applicationId = "io.newm"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = getCurrentDateTimeVersionCode()
        versionName = getCustomVersionName(major = 1)
        testInstrumentationRunner = "io.newm.NewmAndroidJUnitRunner"
        testApplicationId = "io.newm.test"
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isMinifyEnabled = false
        }
    }
    flavorDimensions += "version"

    productFlavors {
        create("production") {
            namespace = "io.newm"
            applicationId = "io.newm"
            dimension = "version"
        }
        create("development") {
            namespace = "io.newm"
            applicationId = "io.newm"
            applicationIdSuffix = ".dev"
            dimension = "version"
        }
        all {
            resValue(
                "string",
                "account_type",
                "$applicationId${applicationIdSuffix.orEmpty()}.account"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.process.phoenix)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.recaptcha)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.koin.android)
    implementation(libs.kotlin.reflect)
    implementation(platform(libs.firebase.bom))
    implementation(project(Modules.barcodeScanner))
    implementation(project(Modules.coreResources))
    implementation(project(Modules.coreTheme))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.login))
    implementation(project(Modules.musicPlayer))
    implementation(project(Modules.shared))

    testImplementation(libs.junit)
    testImplementation(libs.mockk)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.mockk.android)
}


sentry {
    org.set("project-newm")
    projectName.set("android")

    // this will upload your source code to Sentry to show it as part of the stack traces
    // disable if you don't want to expose your sources
    includeSourceContext.set(true)
    telemetry.set(true)
}

/**
 * Generates a dynamic `versionCode` based on the current date and hour.
 *
 * The `versionCode` is constructed using the format `yyyyMMddHH`, which represents:
 * - `yyyy`: 4-digit year
 * - `MM`: 2-digit month (01-12)
 * - `dd`: 2-digit day of the month (01-31)
 * - `HH`: 2-digit hour in 24-hour format (00-23)
 *
 * This ensures that each build has a unique `versionCode` per hour of the day.
 *
 * @return An integer representing the dynamically generated `versionCode`.
 */
fun getCurrentDateTimeVersionCode(): Int {
    val dateFormat = SimpleDateFormat("yyyyMMddHH")
    return dateFormat.format(Date()).toInt()
}

/**
 * Generates a dynamic `versionName` based on a specified major version, current year, month, day, and hour.
 *
 * The `versionName` follows the format `Major.Year.MMDDHH`, where:
 * - `Major`: The major version number provided as an input parameter.
 * - `Year`: The current year (e.g., 2024).
 * - `MMDD`: Month and day combined (e.g., 0925 for September 25).
 * - `HH`: Hour in 24-hour format (00-23).
 *
 * @param major The major version number to be included in the `versionName`.
 * @return A string representing the dynamically generated `versionName`.
 */
fun getCustomVersionName(major: Int): String {
    val yearFormat = SimpleDateFormat("yyyy")
    val monthDayFormat = SimpleDateFormat("MMdd")
    val hourFormat = SimpleDateFormat("HH")

    val year = yearFormat.format(Date())
    val monthDay = monthDayFormat.format(Date())
    val hour = hourFormat.format(Date())

    return "$major.$year.$monthDay$hour"
}