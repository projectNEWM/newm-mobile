package io.newm.shared.public.analytics

/**
 * Represents a screen event in the application for analytics tracking.
 *
 * Each screen event contains a unique screen name that identifies the screen
 * within the application. Implementations of this interface should provide
 * additional context or metadata associated with the screen event
 */
interface ScreenEvents {
    val name: String
}