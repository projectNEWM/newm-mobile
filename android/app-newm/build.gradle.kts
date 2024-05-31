import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties

apply(from = "../../gradle_include/compose.gradle")
apply(from = "../../gradle_include/circuit.gradle")
apply(from = "../../gradle_include/flipper.gradle")

plugins {
    id(Plugins.androidApplication)
    id(Plugins.crashlytics)
    id(Plugins.googleServices)
    id(Plugins.parcelize)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
}

val properties: Properties = gradleLocalProperties(rootDir)

android {
    compileSdk = Versions.androidCompileSdk

    namespace = "io.newm"
    testNamespace = "io.newm.test"
    defaultConfig {
        applicationId = "io.newm"
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        versionCode = 3
        versionName = "0.2"
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

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation("com.jakewharton:process-phoenix:2.1.2")
    implementation(Google.androidxCore)
    implementation(Google.appCompat)
    implementation(Google.constraintLayout)
    implementation(Google.firebaseAnalytics)
    implementation(Google.firebaseCrashlytics)
    implementation(Google.lifecycle)
    implementation(Google.material)
    implementation(Google.navigationUiKtx)
    implementation(Google.playServicesAuth)
    implementation(Google.recaptcha)
    implementation(Google.splashScreen)
    implementation(Koin.android)
    implementation(Kotlin.reflect)
    implementation(platform(Google.firebase))
    implementation(project(Modules.barcodeScanner))
    implementation(project(Modules.coreResources))
    implementation(project(Modules.coreTheme))
    implementation(project(Modules.coreUiUtils))
    implementation(project(Modules.login))
    implementation(project(Modules.musicPlayer))
    implementation(project(Modules.shared))

    testImplementation(JUnit.jUnit)
    testImplementation(Mockk.mockk)

    androidTestImplementation(Google.espressoTest)
    androidTestImplementation(JUnit.androidxJUnit)
    androidTestImplementation(Mockk.android)
}
