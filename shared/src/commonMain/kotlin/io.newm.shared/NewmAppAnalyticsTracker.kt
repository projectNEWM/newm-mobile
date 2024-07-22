package io.newm.shared

class NewmAppAnalyticsTracker : AppAnalyticsTracker {

    private var appAnalyticsTracker: AppAnalyticsTracker? = null

    fun setClientAnalyticsTracker(clientTracker: AppAnalyticsTracker) {
        appAnalyticsTracker = clientTracker
    }

    override fun trackEvent(eventName: String, properties: Map<String, Any?>?) {
        appAnalyticsTracker?.trackEvent(eventName, properties)
    }

    override fun trackScreenView(screenName: String) {
        appAnalyticsTracker?.trackScreenView(screenName)
    }

    override fun setUserId(userId: String) {
        appAnalyticsTracker?.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String) {
        appAnalyticsTracker?.setUserProperty(name, value)
    }

    override fun trackScroll(startPosition: Int, endPosition: Int, screenName: String) {
        appAnalyticsTracker?.trackScroll(startPosition, endPosition, screenName)
    }

    override fun trackButtonInteraction(buttonName: String, eventType: String) {
        appAnalyticsTracker?.trackButtonInteraction(buttonName, eventType)
    }

    override fun trackPlayButtonClick(songId: String, songName: String) {
        appAnalyticsTracker?.trackPlayButtonClick(songId, songName)
    }

    override fun trackAppLaunch() {
        appAnalyticsTracker?.trackAppLaunch()
    }

    override fun trackAppClose() {
        appAnalyticsTracker?.trackAppClose()
    }

    override fun trackUserScroll(percentage: Double) {
        appAnalyticsTracker?.trackUserScroll(percentage)
    }
}