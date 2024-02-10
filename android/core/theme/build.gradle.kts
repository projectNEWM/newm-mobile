plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
}

apply(from = "../../../gradle_include/compose.gradle")

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm.core.theme"

    defaultConfig {
        minSdk = Versions.androidMinSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(Google.androidxCore)
    implementation(Google.appCompat)
    implementation(Google.constraintLayout)
    implementation(Google.material)

    testImplementation(JUnit.jUnit)

    androidTestImplementation(Google.espressoTest)
    androidTestImplementation(JUnit.androidxJUnit)
}