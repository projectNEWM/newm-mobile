package io.newm.shared.public.analytics

class NewmAppEventLogger : IEventLogger {

    private var eventLogger: IEventLogger? = null

    fun setClientAnalyticsTracker(clientTracker: IEventLogger) {
        eventLogger = clientTracker
    }

    override fun logEvent(eventName: String, properties: Map<String, Any?>?) {
        eventLogger?.logEvent(eventName, properties)
    }

    override fun logPageLoad(screenName: String, properties: Map<String, Any?>?) {
        eventLogger?.logPageLoad(screenName, properties)
    }

    override fun setUserId(userId: String) {
        eventLogger?.setUserId(userId)
    }

    override fun setUserProperty(propertyName: String, value: String) {
        eventLogger?.setUserProperty(propertyName, value)
    }

    override fun logClickEvent(buttonName: String, properties: Map<String, Any?>?) {
        eventLogger?.logClickEvent(buttonName, properties)
    }
}