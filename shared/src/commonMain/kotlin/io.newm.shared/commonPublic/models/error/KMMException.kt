package io.newm.shared.commonPublic.models.error

/**
 * An open class representing a custom exception in the context of Kotlin Multiplatform Mobile
 * (KMM).
 *
 * `KMMException` extends the standard `Throwable` class, allowing for the creation of specialized
 * exceptions tailored to specific needs and scenarios encountered in KMM projects. It can be used
 * to encapsulate error details more descriptively and is intended for scenarios where standard
 * exceptions do not suffice.
 *
 * @property message A detailed message describing the exception. This message can be used to
 *   provide more context about the error, making it easier to diagnose issues.
 * @param message The message describing the specific error that occurred.
 * @constructor Creates a new instance of `KMMException` with the specified error message.
 */
open class KMMException(
    message: String,
    val wrappedException: Throwable? = null,
) : Throwable(message)
