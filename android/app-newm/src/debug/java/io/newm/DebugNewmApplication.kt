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

        SoLoader.init(this, false)
        val client = AndroidFlipperClient.getInstance(this)
        client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
        client.addPlugin(CrashReporterPlugin.getInstance())
        client.addPlugin(DatabasesFlipperPlugin(this))
        client.addPlugin(NavigationFlipperPlugin.getInstance())
        client.addPlugin(SharedPreferencesFlipperPlugin(this, "newm_encrypted_shared_prefs"))
        client.start()

        super.onCreate()
    }
}