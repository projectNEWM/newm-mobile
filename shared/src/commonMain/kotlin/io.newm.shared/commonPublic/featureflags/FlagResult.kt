package io.newm.shared.commonPublic.featureflags

// Result wrapper for error handling
sealed class FlagResult<out T> {
    data class Success<T>(val value: T) : FlagResult<T>()
    data class Error<T>(val exception: Throwable, val fallback: T? = null) : FlagResult<T>()

    inline fun <R> fold(
        onSuccess: (T) -> R,
        onError: (Throwable, T?) -> R
    ): R = when (this) {
        is Success -> onSuccess(value)
        is Error -> onError(exception, fallback)
    }
}