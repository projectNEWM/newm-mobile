package io.newm.shared.public.analytics

class NewmAppEventLogger : IEventLogger {

    private var eventLogger: IEventLogger? = null

    fun setClientAnalyticsTracker(clientTracker: IEventLogger) {
        eventLogger = clientTracker
    }

    override fun logEvent(eventName: String, properties: Map<String, Any?>?) {
        eventLogger?.logEvent(eventName, properties)
    }

    override fun logPageLoad(screenName: String) {
        eventLogger?.logPageLoad(screenName)
    }

    override fun setUserId(userId: String) {
        eventLogger?.setUserId(userId)
    }

    override fun setUserProperty(name: String, value: String) {
        eventLogger?.setUserProperty(name, value)
    }

    override fun logScroll(startPosition: Int, endPosition: Int, screenName: String) {
        eventLogger?.logScroll(startPosition, endPosition, screenName)
    }

    override fun logClickEvent(buttonName: String, eventType: String) {
        eventLogger?.logClickEvent(buttonName, eventType)
    }

    override fun logPlayButtonClick(songId: String, songName: String) {
        eventLogger?.logPlayButtonClick(songId, songName)
    }

    override fun logAppLaunch() {
        eventLogger?.logAppLaunch()
    }

    override fun logAppClose() {
        eventLogger?.logAppClose()
    }

    override fun logUserScroll(percentage: Double) {
        eventLogger?.logUserScroll(percentage)
    }
}