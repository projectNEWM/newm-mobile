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
        "platform" to "Android"
    )

    companion object {
        private const val MAX_EVENT_NAME_LENGTH =
            32 // Firebase allows 40, but using 32 for readability
        private const val TAG = "AnalyticsTracker" // Tag for logging
    }

    override fun setUserId(userId: String) {
        logger.debug(tag = "analytics - user", message = "userId: $userId")
        firebaseAnalytics.setUserId(userId)
    }

    override fun setUserProperty(propertyName: String, value: String) {
        logger.debug(
            tag = "analytics - user",
            message = "propertyName: $propertyName, value:$value"
        )
        firebaseAnalytics.setUserProperty(propertyName, value)
    }

    override fun logEvent(eventName: String, properties: Map<String, Any?>?) {
        logger.debug(
            tag = "analytics - event",
            message = "eventName: $eventName, properties:$properties"
        )
        actualLogEvent(eventName, properties)
    }

    override fun logPageLoad(screenName: String, properties: Map<String, Any?>?) {
        logger.debug(
            tag = "analytics - screen",
            message = "screen: $screenName"
        )

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            properties?.forEach { (key, value) ->
                param(key, value.toString())
            }
        }
    }

    override fun logClickEvent(
        buttonName: String,
        properties: Map<String, Any?>?
    ) {
        logger.debug(
            tag = "analytics - click",
            message = "buttonName: $buttonName"
        )
        actualLogEvent(
            eventName = "button_click",
            properties = mapOf("button_name" to buttonName,) + (properties ?: emptyMap())
        )
    }

    private fun actualLogEvent(eventName: String, properties: Map<String, Any?>?) {
        val safeEventName = validateEventName(eventName)
        if (safeEventName == null) {
            logger.info(TAG, "Invalid event name: $eventName. Event not tracked.")
            return
        }
        val metadata = mapOf(
            "timestamp" to System.currentTimeMillis()
        )
        val combinedProperties = defaultProperties + (properties ?: emptyMap()) + metadata
        val bundle = bundleOf(*combinedProperties.toList().toTypedArray())
        firebaseAnalytics.logEvent(safeEventName, bundle)
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
