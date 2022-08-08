plugins {
    id(Plugins.androidLibrary)
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

    implementation(project(Modules.shared))

    implementation(Google.androidxCore)
    implementation(Google.appCompat)
    implementation(Google.composeMaterial)
    implementation(Google.composeUi)
    implementation(Google.composeUiToolingPreview)
    implementation(Google.constraintLayout)
    implementation(Google.material)
    implementation(Google.navigationCompose)
    implementation(Koin.android)
    implementation(Koin.androidCompose)

    testImplementation(JUnit.jUnit)

    androidTestImplementation(JUnit.androidxJUnit)
    androidTestImplementation(Google.espressoTest)
}