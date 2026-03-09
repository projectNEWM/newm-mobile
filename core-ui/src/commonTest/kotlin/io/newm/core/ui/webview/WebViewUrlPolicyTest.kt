package io.newm.core.ui.webview

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WebViewUrlPolicyTest {
    @Test
    fun `allows exact internal hosts`() {
        assertTrue(isInternalUrl("https://newm.io"))
        assertTrue(isInternalUrl("https://newm.studio/home/library"))
    }

    @Test
    fun `allows internal subdomains`() {
        assertTrue(isInternalUrl("https://recordstore.newm.io"))
        assertTrue(isInternalUrl("https://marketplace.newm.io"))
    }

    @Test
    fun `rejects lookalike external domains`() {
        assertFalse(isInternalUrl("https://evilnewm.io"))
        assertFalse(isInternalUrl("https://newm.io.evil.com"))
    }

    @Test
    fun `rejects malformed or non http urls`() {
        assertFalse(isInternalUrl("mailto:support@newm.io"))
        assertFalse(isInternalUrl("intent://marketplace.newm.io"))
        assertFalse(isInternalUrl("ftp://newm.io"))
        assertFalse(isInternalUrl("not-a-url"))
    }
}
