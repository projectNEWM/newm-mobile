object Google {
    const val activityCompose = "androidx.activity:activity-compose:${Versions.androidxActivity}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.androidxCore}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val materialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.composeMaterialIcons}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayout}"
    const val espressoTest = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val firebase = "com.google.firebase:firebase-bom:${Versions.firebase}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val crashlyticsClasspath = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsClasspath}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycle}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.androidxNavigation}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.androidxNavigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.androidxNavigation}"
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.androidxSplashScreen}"

    object Test {
        const val composeUiTestJUnit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    }
}