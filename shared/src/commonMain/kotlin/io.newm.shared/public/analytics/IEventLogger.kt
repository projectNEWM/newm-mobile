package io.newm.shared.public.analytics

/**
 * Interface for tracking analytics events in the application.
 */
interface IEventLogger {

    /**
     * Tracks a custom event with the given name and properties.
     *
     * @param eventName The name of the event to track.
     * @param properties A map of properties associated with the event.
     */
    fun logEvent(eventName: String, properties: Map<String, Any?>?)

    /**
     * Tracks a screen view event.
     *
     * @param screenName The name of the screen being viewed.
     */
    fun logPageLoad(screenName: String)

    /**
     * Sets the user ID for tracking purposes.
     *
     * @param userId The ID of the user.
     */
    fun setUserId(userId: String)

    /**
     * Sets a user property.
     *
     * @param name The name of the property.
     * @param value The value of the property.
     */
    fun setUserProperty(name: String, value: String)

    /**
     * Tracks a scroll event.
     *
     * @param startPosition The starting position of the scroll.
     * @param endPosition The ending position of the scroll.
     * @param screenName The name of the screen where the scroll happened.
     */
    fun logScroll(startPosition: Int, endPosition: Int, screenName: String)

    /**
     * Tracks a button interaction event.
     *
     * @param buttonName The name of the button being interacted with.
     * @param eventType The type of the event (default is "button_click").
     */
    fun logClickEvent(buttonName: String, eventType: String = "button_click")

    /**
     * Tracks a play button click event with song details.
     *
     * @param songId The ID of the song being played.
     * @param songName The name of the song being played.
     */
    fun logPlayButtonClick(songId: String, songName: String)

    /**
     * Tracks the app launch event.
     */
    fun logAppLaunch()

    /**
     * Tracks the app close event.
     */
    fun logAppClose()

    /**
     * Tracks a user scroll event with a scroll percentage.
     *
     * @param percentage The percentage of the scroll.
     */
    fun logUserScroll(percentage: Double)
}