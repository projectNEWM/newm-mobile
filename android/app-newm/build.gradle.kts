
apply(from = "../../gradle_include/compose.gradle")
apply(from = "../../gradle_include/circuit.gradle")
apply(from = "../../gradle_include/flipper.gradle")

plugins {
    id("com.android.application")
    id( "com.google.gms.google-services")
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
        versionCode = 5
        versionName = "0.3.0"
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
