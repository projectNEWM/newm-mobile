package io.newm

import co.touchlab.kermit.Logger
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader

class DebugNewmApplication : NewmApplication() {
    override fun onCreate() {
        Logger.d { "NewmAndroid - Debug Newm Application" }

        initKoin(enableNetworkLogs = true)

        SoLoader.init(this, false)
        AndroidFlipperClient.getInstance(this).apply {
            addPlugin(
                InspectorFlipperPlugin(
                    this@DebugNewmApplication,
                    DescriptorMapping.withDefaults()
                )
            )
            addPlugin(CrashReporterPlugin.getInstance())
            addPlugin(DatabasesFlipperPlugin(this@DebugNewmApplication))
            addPlugin(NavigationFlipperPlugin.getInstance())
            addPlugin(
                SharedPreferencesFlipperPlugin(
                    this@DebugNewmApplication,
                    "newm_encrypted_shared_prefs"
                )
            )
        }.start()

        super.onCreate()
    }
}