package io.newm.utils

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import io.newm.BuildConfig
import io.newm.shared.NewmAppLogger
import io.newm.shared.public.analytics.IEventLogger

/**
 * Implementation of [IEventLogger] for Android using Firebase Analytics.
 */
internal class AndroidEventLoggerImpl(val logger: NewmAppLogger) : IEventLogger {

    private val firebaseAnalytics = Firebase.analytics

    private val defaultProperties = mapOf(
        "app_version" to BuildConfig.VERSION_NAME,
        "platform" to "Android",
    )

    companion object {
        private const val MAX_EVENT_NAME_LENGTH =
            32 // Firebase allows 40, but using 32 for readability
        private const val TAG = "AnalyticsTracker" // Tag for logging
    }

    override fun logEvent(eventName: String, properties: Map<String, Any?>?) {
        val safeEventName = validateEventName(eventName)
        if (safeEventName == null) {
            logger.info(TAG, "Invalid event name: $eventName. Event not tracked.")
            return
        }
        val combinedProperties = defaultProperties + (properties ?: emptyMap())
        val bundle = bundleOf(*combinedProperties.toList().toTypedArray())
        firebaseAnalytics.logEvent(safeEventName, bundle)
    }

    override fun logPageLoad(screenName: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
    }

    override fun setUserId(userId: String) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String) {
        firebaseAnalytics.setUserProperty(name, value)
    }

    override fun logScroll(startPosition: Int, endPosition: Int, screenName: String) {
        logEvent(
            "scroll", mapOf(
                "scroll_position_start" to startPosition,
                "scroll_position_end" to endPosition,
                "screen_name" to screenName
            )
        )
    }

    override fun logClickEvent(buttonName: String, eventType: String) {
        logEvent(eventType, mapOf("button_name" to buttonName))
    }

    override fun logPlayButtonClick(songId: String, songName: String) {
        logEvent(
            "play_button_click", mapOf(
                "song_id" to songId,
                "song_name" to songName
            )
        )
    }

    override fun logAppLaunch() {
        logEvent("app_launch", null)
    }

    override fun logAppClose() {
        logEvent("app_close", null)
    }

    override fun logUserScroll(percentage: Double) {
        logEvent("user_scroll", mapOf("scroll_percentage" to percentage))
    }

    private fun validateEventName(name: String): String? {
        if (name.startsWith("firebase_") || name.startsWith("google_") || name.startsWith("ga_")) {
            return null
        }

        val cleanedName = name.replace(Regex("[^A-Za-z0-9_]"), "_")
        return if (cleanedName.length > MAX_EVENT_NAME_LENGTH) {
            cleanedName.substring(0, MAX_EVENT_NAME_LENGTH)
        } else {
            cleanedName
        }
    }
}
