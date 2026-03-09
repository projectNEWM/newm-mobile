package io.newm.core.ui.webview

private val internalHosts = setOf("newm.io", "newm.studio")
private val internalSchemes = setOf("http", "https")

internal fun isInternalUrl(url: String): Boolean {
    val parsedUrl = parseUrl(url) ?: return false
    if (parsedUrl.scheme !in internalSchemes) return false
    return internalHosts.any { allowedHost ->
        parsedUrl.host == allowedHost || parsedUrl.host.endsWith(".$allowedHost")
    }
}

private fun parseUrl(url: String): ParsedUrl? {
    val scheme =
        url
            .substringBefore("://", missingDelimiterValue = "")
            .takeIf { it.isNotBlank() }
            ?.lowercase() ?: return null

    val host =
        url
            .substringAfter("://", missingDelimiterValue = "")
            .substringBefore('/')
            .substringBefore('?')
            .substringBefore('#')
            .substringBefore(':')
            .takeIf { it.isNotBlank() }
            ?.lowercase() ?: return null

    return ParsedUrl(scheme = scheme, host = host)
}

private data class ParsedUrl(
    val scheme: String,
    val host: String,
)
