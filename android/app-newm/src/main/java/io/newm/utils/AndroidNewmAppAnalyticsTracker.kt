package io.newm.utils

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
import io.newm.shared.NewmAppLogger
import io.newm.shared.public.analytics.AppAnalyticsTracker

/**
 * Implementation of [AppAnalyticsTracker] for Android using Firebase Analytics.
 */
internal class AndroidNewmAppAnalyticsTracker(val logger: NewmAppLogger) : AppAnalyticsTracker {

    private val firebaseAnalytics = Firebase.analytics

    companion object {
        private const val MAX_EVENT_NAME_LENGTH =
            32 // Firebase allows 40, but using 32 for readability
        private const val TAG = "AnalyticsTracker" // Tag for logging
    }

    override fun trackEvent(eventName: String, properties: Map<String, Any?>?) {
        val safeEventName = validateEventName(eventName)
        if (safeEventName == null) {
            logger.info(TAG, "Invalid event name: $eventName. Event not tracked.")
            return
        }
        val bundle = bundleOf(*properties?.toList()?.toTypedArray().orEmpty())
        firebaseAnalytics.logEvent(safeEventName, bundle)
    }

    override fun trackScreenView(screenName: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenName) // Optional, if relevant
        }
    }

    override fun setUserId(userId: String) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String) {
        firebaseAnalytics.setUserProperty(name, value)
    }

    override fun trackScroll(startPosition: Int, endPosition: Int, screenName: String) {
        trackEvent(
            "scroll", mapOf(
                "scroll_position_start" to startPosition,
                "scroll_position_end" to endPosition,
                "screen_name" to screenName
            )
        )
    }

    override fun trackButtonInteraction(buttonName: String, eventType: String) {
        trackEvent(eventType, mapOf("button_name" to buttonName))
    }

    override fun trackPlayButtonClick(songId: String, songName: String) {
        trackEvent(
            "play_button_click", mapOf(
                "song_id" to songId,
                "song_name" to songName
            )
        )
    }

    override fun trackAppLaunch() {
        trackEvent("app_launch", null)
    }

    override fun trackAppClose() {
        trackEvent("app_close", null)
    }

    override fun trackUserScroll(percentage: Double) {
        trackEvent("user_scroll", mapOf("scroll_percentage" to percentage))
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
