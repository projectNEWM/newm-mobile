package io.newm.shared.internal.useCases.utilities

import io.newm.shared.public.models.error.KMMException
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