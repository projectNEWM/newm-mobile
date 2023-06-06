object Plugins {
    const val android = "android"
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val crashlytics = "com.google.firebase.crashlytics"
    const val googleServices = "com.google.gms.google-services"
    const val kapt = "kapt"
    const val multiplatform = "multiplatform"
    const val serialization = "plugin.serialization"
    const val kotlinxSerialization = "kotlinx-serialization"
    const val sqlDelight = "com.squareup.sqldelight"

    const val benManesVersionsPlugin = "com.github.ben-manes.versions"
    const val benManesVersionsClasspath = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersionsPlugin}"

    const val paparazziClassPath = "app.cash.paparazzi:paparazzi-gradle-plugin:${Versions.paparazzi}"
    const val paparazzi = "app.cash.paparazzi"
}
