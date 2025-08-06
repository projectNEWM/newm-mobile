package io.newm.shared.commonInternal.implementations.utilities

import io.newm.shared.commonPublic.models.error.KMMException
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> mapErrorsSuspend(block: suspend () -> T): T {
    try {
        return block()
    } catch (e: KMMException) {
        throw e
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        throw KMMException("We encountered a problem.", e)
    }
}

fun <T> mapErrors(block: () -> T): T {
    try {
        return block()
    } catch (e: KMMException) {
        throw e
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        throw KMMException("We encountered a problem", e)
    }
}

