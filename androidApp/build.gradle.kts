plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        applicationId = "com.projectnewm"
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
        versionCode = 1
        versionName = "1.0"
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(Deps.androidxAppCompat)
    implementation(Deps.androidxConstraintLayout)
    implementation(Deps.androidxCore)
    implementation(Deps.androidxNavigationFragmentKtx)
    implementation(Deps.androidxNavigationUiKtx)
    implementation(Deps.androidxSplashScreen)
    implementation(Deps.kotlinStdLib)
    implementation(Deps.material)
    implementation(Deps.viewBindingPropertyDelegate)

    testImplementation(Deps.junit)

    androidTestImplementation(Deps.androidxJUnit)
    androidTestImplementation(Deps.espressoTest)
}