plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
}

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm.core.theme"

    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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

//    kapt(Airbnb.showkaseProcessor)

//    implementation(Airbnb.showkase)
    implementation(Google.androidxCore)
    implementation(Google.appCompat)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.constraintLayout)
    implementation(Google.material)

    testImplementation(JUnit.jUnit)

    androidTestImplementation(JUnit.androidxJUnit)
    androidTestImplementation(Google.espressoTest)
}