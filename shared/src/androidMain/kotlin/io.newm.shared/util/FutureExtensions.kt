package io.newm.shared.util

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Future

/**
 * Converts a Java Future to a Kotlin Deferred. Useful for bridging Java concurrent APIs with Kotlin
 * coroutines.
 */
suspend fun <V> Future<V>.asDeferred(): Deferred<V> {
    val deferred = CompletableDeferred<V>()
    withContext(Dispatchers.IO) {
        try {
            deferred.complete(get())
        } catch (e: Exception) {
            deferred.completeExceptionally(e)
        }
    }
    return deferred
}
