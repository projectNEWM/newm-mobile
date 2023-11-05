plugins {
    id(Plugins.androidApplication)
    id(Plugins.crashlytics)
    id(Plugins.googleServices)
    id(Plugins.parcelize)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
}

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm"
    testNamespace = "io.newm.test"
    defaultConfig {
        applicationId = "io.newm"
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        versionCode = 1
        versionName = "0.1"
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
            versionNameSuffix = "-dev"
            dimension = "version"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    kapt {
        correctErrorTypes = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

//    kapt(Airbnb.showkaseProcessor)
    implementation(platform(Google.firebase))
    implementation(project(Modules.coreTheme))
    implementation(project(Modules.coreResources))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.login))
    implementation(project(Modules.musicPlayer))
    implementation(project(Modules.barcodeScanner))
    implementation(project(Modules.shared))

    implementation(Circuit.foundation)
    implementation(Circuit.retained)

//    implementation(Airbnb.showkase)
    implementation(Google.activityCompose)
    implementation(Google.androidxCore)
    implementation(Google.appCompat)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.constraintLayout)
    implementation(Google.firebaseAnalytics)
    implementation(Google.firebaseCrashlytics)
    implementation(Google.lifecycle)
    implementation(Google.material)
    implementation(Google.navigationCompose)
    implementation(Google.navigationUiKtx)
    implementation(Google.splashScreen)
    implementation(Google.playServicesAuth)
    implementation(Koin.android)
    implementation(Koin.androidCompose)
    implementation(Kotlin.reflect)
    implementation(Coil.compose)
    implementation("com.jakewharton:process-phoenix:2.1.2")

    debugImplementation(Facebook.flipper)
    debugImplementation(Facebook.soloader)
    releaseImplementation(Facebook.flipperNoop)

    debugImplementation(Google.composeUiTooling)
    debugImplementation(Google.composeUiTestManifest)

    testImplementation(JUnit.jUnit)
    testImplementation(Mockk.mockk)

    androidTestImplementation(Google.espressoTest)
    androidTestImplementation(Google.Test.composeUiTestJUnit)
    androidTestImplementation(JUnit.androidxComposeJUnit)
    androidTestImplementation(JUnit.androidxJUnit)
    androidTestImplementation(Mockk.android)
}
