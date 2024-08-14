package io.newm.screens.forceupdate

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openAppPlayStore() {
    // Build the Play Store URL using the package name
    val appPackageName = packageName // Current app's package name
    val playStoreUrl = "https://play.google.com/store/apps/details?id=$appPackageName"

    try {
        // Attempt to open the Play Store app
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl)))
    } catch (e: Exception) {
        // If Play Store app is not available, open in the web browser
        val webPlayStoreUrl = "https://play.google.com/store/apps/details?id=$appPackageName"
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webPlayStoreUrl)))
    }
}