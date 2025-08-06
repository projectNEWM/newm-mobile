@file:OptIn(ExperimentalTime::class)

package io.newm.shared.commonPublic.featureflags

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

// Thread-safe cache entry with TTL
data class CacheEntry constructor(
    val value: Boolean,
    val timestamp: Instant,
    val ttl: Duration
) {
    fun isExpired(now: Instant): Boolean = (now - timestamp) > ttl
}