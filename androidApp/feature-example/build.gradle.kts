plugins {
    id(Plugins.androidLibrary)
    id(Plugins.hilt)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    kapt(Hilt.hiltCompiler)
    kapt(Hilt.hiltAndroidCompiler)

    implementation(project(Modules.shared))

    implementation(Google.androidxCore)
    implementation(Google.appCompat)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.constraintLayout)
    implementation(Google.material)
    implementation(Google.navigationCompose)
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltNavigation)
    implementation(Hilt.hiltNavigationCompose)

    testImplementation(JUnit.jUnit)

    androidTestImplementation(JUnit.androidxJUnit)
    androidTestImplementation(Google.espressoTest)
}