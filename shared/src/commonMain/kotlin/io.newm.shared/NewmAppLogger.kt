package io.newm.shared

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * A concrete implementation of [AppLogger] that delegates logging
 * to another [AppLogger] instance. Allows setting a client-specific
 * logger implementation.
 */
class NewmAppLogger : AppLogger {

    private var appLogger: AppLogger? = null

    /**
     * Sets the client-specific logger implementation.
     *
     * @param clientImplementation The client-specific [AppLogger] implementation to delegate logging to.
     */
    fun setClientLogger(clientImplementation: AppLogger) {
        appLogger = clientImplementation
    }

    /**
     * Sets the user identifier for the logging context.
     *
     * @param userId The identifier of the user.
     */
    override fun user(userId: String) {
        appLogger?.user(userId)
    }

    /**
     * Logs a debug message.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The debug message to log.
     */
    override fun debug(tag: String, message: String) {
        appLogger?.debug(tag, message)
    }

    /**
     * Logs an informational message.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The informational message to log.
     */
    override fun info(tag: String, message: String) {
        appLogger?.info(tag, message)
    }

    /**
     * Logs an error message along with an exception.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The error message to log.
     * @param exception The exception associated with the error.
     */
    override fun error(tag: String, message: String, exception: Throwable) {
        appLogger?.error(tag, message, exception)
    }

    /**
     * Logs a breadcrumb message. Breadcrumbs are used to leave a trail of
     * events or actions that can help diagnose issues.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The breadcrumb message to log.
     */
    override fun breadcrumb(tag: String, message: String) {
        appLogger?.breadcrumb(tag, message)
    }
}


class NewmAppLoggerProvider : KoinComponent {
    private val newmCrashReporter: NewmAppLogger by inject()

    fun get(): NewmAppLogger {
        return this.newmCrashReporter
    }
}
