import io.newm.shared.utils.VersionUtils
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VersionUtilsTest {

    @Test
    fun `test version comparison with pre-release versions`() {
        assertFalse(VersionUtils.isVersionGreaterThan("1.0.0-alpha", "1.0.0-beta"), "1.0.0-alpha should not be greater than 1.0.0-beta")
        assertTrue(VersionUtils.isVersionGreaterThan("1.0.0-beta", "1.0.0-alpha"), "1.0.0-beta should be greater than 1.0.0-alpha")
        assertTrue(VersionUtils.isVersionGreaterThan("1.0.0", "1.0.0-alpha"), "1.0.0 should be greater than 1.0.0-alpha")
        assertTrue(VersionUtils.isVersionGreaterThan("1.0.1", "1.0.0"), "1.0.1 should be greater than 1.0.0")
    }

    @Test
    fun `test version comparison with same versions`() {
        assertFalse(VersionUtils.isVersionGreaterThan("1.0.0", "1.0.0"))
    }

    @Test
    fun `test version comparison with major differences`() {
        assertTrue(VersionUtils.isVersionGreaterThan("2.0.0", "1.9.9"))
    }
}