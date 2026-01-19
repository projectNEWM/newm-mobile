package io.newm.sharedfeatures.fakes

import io.newm.shared.commonPublic.analytics.IEventLogger

class FakeEventLogger : IEventLogger {
    val clickEvents = mutableListOf<Pair<String, Map<String, Any?>?>>()
    val pageLoads = mutableListOf<Pair<String, Map<String, Any?>?>>()

    override fun setUserId(userId: String) {}
    override fun setUserProperty(propertyName: String, value: String) {}
    override fun logEvent(eventName: String, properties: Map<String, Any?>?) {}
    override fun logPageLoad(screenName: String, properties: Map<String, Any?>?) {
        pageLoads.add(screenName to properties)
    }
    override fun logClickEvent(buttonName: String, properties: Map<String, Any?>?) {
        clickEvents.add(buttonName to properties)
    }
}
