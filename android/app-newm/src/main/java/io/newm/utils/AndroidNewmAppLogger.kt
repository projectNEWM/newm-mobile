package io.newm.utils

import android.util.Log
import io.newm.shared.AppLogger
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.sentry.Sentry
import io.sentry.protocol.User
import kotlinx.coroutines.CancellationException
import java.io.IOException

/**
 * An Android-specific implementation of [AppLogger] that logs messages
 * using Android's [Log] class and integrates with Sentry for error tracking.
 */
internal class AndroidNewmAppLogger(private val analyticsTracker: NewmAppEventLogger) : AppLogger {

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
        analyticsTracker.setUserId(userId)
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
     * Logs an error using Android's [Log] and optionally reports the exception to Sentry.
     * Certain exceptions, like [CancellationException] and [IOException], are excluded from reporting.
     *
     * @param tag The tag identifying the source of the log message.
     * @param message The error message to log.
     * @param exception The exception to log and, if not excluded, report to Sentry.
     */
    override fun error(tag: String, message: String, exception: Throwable) {
        Log.e(tag, message, exception)
        val exceptionsNotToReport = setOf(CancellationException::class, IOException::class)
        // Only send to Sentry if the exception is not in the blacklist
        if (exceptionsNotToReport.none { it.isInstance(exception) }) {
            Sentry.captureException(exception)
        } else {
            // Optionally log at a different level for known exceptions, like warnings
            Log.w(tag, "Excluded from Sentry reporting: ${exception::class.simpleName} - $message", exception)
        }
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
