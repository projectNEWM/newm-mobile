plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.4"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(Deps.material)
    implementation(Deps.viewBindingPropertyDelegate)
    implementation(Deps.androidHilt)
    kapt(Deps.androidHiltCompiler)
    kapt(Deps.hiltCompiler)
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.compose.ui:ui:1.0.5")
    implementation("androidx.compose.material:material:1.0.5")
    implementation("androidx.compose.ui:ui-tooling-preview:1.0.5")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.navigation:navigation-compose:2.4.0-rc01")
    implementation ("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.5")
    debugImplementation("androidx.compose.ui:ui-tooling:1.0.5")
    testImplementation(Deps.junit)

    androidTestImplementation(Deps.androidxJUnit)
    androidTestImplementation(Deps.espressoTest)
}