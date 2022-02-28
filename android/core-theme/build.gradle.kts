plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    androidTestImplementation(JUnit.androidxJUnit)
    androidTestImplementation(Google.espressoTest)

    implementation(Google.androidxCore)
    implementation(Google.appCompat)
    implementation(Google.material)
    implementation(Google.constraintLayout)

    testImplementation(JUnit.jUnit)
}