plugins {
    id(Plugins.androidApplication)
    id(Plugins.hilt)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "io.projectnewm.demo"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltAndroidCompiler)

    implementation(project(Modules.coreTheme))
    implementation(project(Modules.featureExample))
    implementation(project(Modules.shared))

    implementation(Google.activityCompose)
    implementation(Google.androidxCore)
    implementation(Google.appCompat)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.material)
    implementation(Google.navigationCompose)
    implementation(Google.navigationFragmentKtx)
    implementation(Google.navigationUiKtx)
    implementation(Google.constraintLayout)
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltNavigation)
    implementation(Hilt.hiltNavigationCompose)
    implementation(Ktor.android)

    debugImplementation(Google.composeUiTooling)
    debugImplementation(Google.composeUiTestManifest)

    testImplementation(JUnit.jUnit)

    androidTestImplementation(JUnit.androidxJUnit)
    androidTestImplementation(Google.espressoTest)
}