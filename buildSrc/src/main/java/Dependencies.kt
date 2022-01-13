import org.gradle.api.artifacts.dsl.RepositoryHandler

@Suppress("unused")
object Versions {
    const val compileSdkVersion = 31
    const val minSdkVersion = 25
    const val targetSdkVersion = 31
    const val androidGradlePlugin = "7.0.4"
    const val gradleVersionsPlugin = "0.39.0"

    const val kotlin = "1.5.31"
    const val kotlinCoroutines = "1.5.31"

    const val androidxActivity = "1.4.0"
    const val androidxAppCompat = "1.4.0"
    const val androidxConstraintLayout = "2.1.2"
    const val androidxCore = "1.7.0"
    const val androidxJUnit = "1.1.3"
    const val androidxLifecycle = "2.4.0"
    const val androidxNavigation = "2.3.5"
    const val androidxSplashScreen = "1.0.0-alpha02"
    const val material = "1.4.0"
    const val viewBindingPropertyDelegate = "1.5.3"

    const val hilt = "2.39"
    const val espresso = "3.4.0"
    const val junit = "4.13.2"
}

@Suppress("unused")
object Deps {
    object Plugins {
        const val gradle = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
        const val gradleHilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
        const val gradleVersions = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersionsPlugin}"
    }

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val androidxActivity = "androidx.activity:activity-ktx:${Versions.androidxActivity}"
    const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
    const val androidxConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayout}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.androidxCore}"
    const val androidxSplashScreen = "androidx.core:core-splashscreen:1.0.0-alpha01"
    const val androidxJUnit = "androidx.test.ext:junit:${Versions.androidxJUnit}"
    const val androidxNavigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.androidxNavigation}"
    const val androidxNavigationUiKtx =
        "androidx.navigation:navigation-ui-ktx:${Versions.androidxNavigation}"
    const val androidHilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val androidHiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    const val viewBindingPropertyDelegate =
        "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${Versions.viewBindingPropertyDelegate}"

    const val material = "com.google.android.material:material:${Versions.material}"

    const val junit = "junit:junit:${Versions.junit}"
    const val espressoTest = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object Repo {
    @JvmStatic
    fun addRepos(handler: RepositoryHandler) {
        handler.google()
        handler.mavenCentral()
        handler.gradlePluginPortal()
    }
}