package io.newm.shared


/**
 * Interface representing a generic application logger.
 * Provides methods for logging various levels of messages and user information.
 */
interface AppLogger {

    /**
     * Sets the user identifier for the logging context.
     *
     * @param userId The identifier of the user.
     */
    fun user(userId: String)

    /**
     * Logs a debug message.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The debug message to log.
     */
    fun debug(tag: String, message: String)

    /**
     * Logs an informational message.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The informational message to log.
     */
    fun info(tag: String, message: String)

    /**
     * Logs an error message along with an exception.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The error message to log.
     * @param exception The exception associated with the error.
     */
    fun error(tag: String, message: String, exception: Throwable)

    /**
     * Logs a breadcrumb message. Breadcrumbs are used to leave a trail of
     * events or actions that can help diagnose issues.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The breadcrumb message to log.
     */
    fun breadcrumb(tag: String, message: String)
}