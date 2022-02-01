plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    id(Plugins.hilt)
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "com.projectnewm"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = JUnit.jUnitRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    androidTestImplementation(Google.espressoTest)
    androidTestImplementation(JUnit.androidxComposeJUnit)
    androidTestImplementation(JUnit.androidxJUnit)

    debugImplementation(Google.composeUiTooling)
    debugImplementation(Google.composeUiTestManifest)

    implementation(project(Modules.shared))

    implementation(Google.activityCompose)
    implementation(Google.androidxCore)
    implementation(Google.appCompat)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.constraintLayout)
    implementation(Google.lifecycle)
    implementation(Google.material)
    implementation(Google.navigationCompose)
    implementation(Google.navigationFragmentKtx)
    implementation(Google.navigationUiKtx)
    implementation(Google.splashScreen)
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltNavigation)
    implementation(Hilt.hiltNavigationCompose)
    implementation(Kotlin.reflect)
    implementation(Ktor.android)

    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltAndroidCompiler)

    testImplementation(JUnit.jUnit)
}