package io.newm.utils

import android.util.Log
import io.newm.shared.AppLogger
import io.sentry.Sentry
import io.sentry.protocol.User

/**
 * An Android-specific implementation of [AppLogger] that logs messages
 * using Android's [Log] class and integrates with Sentry for error tracking.
 */
internal class AndroidNewmAppLogger : AppLogger {

    /**
     * Sets the user identifier for the logging context and Sentry.
     *
     * @param userId The identifier of the user.
     */
    override fun user(userId: String) {
        Log.i("AndroidNewmAppLogger", "Setting user id: $userId")
        Sentry.setUser(User().apply {
            id = userId
        })
    }

    /**
     * Logs a debug message using Android's [Log] class.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The debug message to log.
     */
    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }

    /**
     * Logs an informational message using Android's [Log] class.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The informational message to log.
     */
    override fun info(tag: String, message: String) {
        Log.i(tag, message)
    }

    /**
     * Logs an error message using Android's [Log] class and reports the exception to Sentry.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The error message to log.
     * @param exception The exception associated with the error.
     */
    override fun error(tag: String, message: String, exception: Throwable) {
        Log.e(tag, message, exception)
        Sentry.captureException(exception)
    }

    /**
     * Logs a breadcrumb message to Sentry. Breadcrumbs are used to leave a trail of
     * events or actions that can help diagnose issues.
     *
     * @param tag The tag identifying the source of the breadcrumb message.
     * @param message The breadcrumb message to log.
     */
    override fun breadcrumb(tag: String, message: String) {
        Sentry.addBreadcrumb(message)
    }
}
