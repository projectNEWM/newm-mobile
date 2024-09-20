package io.newm.shared.public.analytics

/**
 * Interface for tracking analytics events in the application.
 */
interface IEventLogger {

    /**
     * Sets the user ID for tracking purposes.
     *
     * @param userId The ID of the user.
     */
    fun setUserId(userId: String)

    /**
     * Sets a user property.
     *
     * @param propertyName The name of the property.
     * @param value The value of the property.
     */
    fun setUserProperty(propertyName: String, value: String)

    /**
     * Tracks a custom event with the given name and properties.
     *
     * @param eventName The name of the event to track.
     * @param properties A map of properties associated with the event.
     */
    fun logEvent(eventName: String, properties: Map<String, Any?>? = null)

    /**
     * Tracks a screen view event.
     *
     * @param screenName The name of the screen being viewed.
     */
    fun logPageLoad(screenName: String, properties: Map<String, Any?>? = null)

    /**
     * Tracks a button interaction event.
     *
     * @param buttonName The name of the button being interacted with.
     * @param eventType The type of the event (default is "button_click").
     */
    fun logClickEvent(buttonName: String, properties: Map<String, Any?>? = null)
}