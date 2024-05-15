package io.newm.shared.utils

object VersionUtils {

    /**
     * Compares two semantic version strings to determine if the first version is greater than the second.
     *
     * This function parses and compares semantic versions, considering major, minor, patch, and pre-release components.
     * The comparison follows the Semantic Versioning 2.0.0 specification where:
     * - Major, minor, and patch versions are compared numerically.
     * - Pre-release versions have a lower precedence than the associated normal version.
     * - A version with a pre-release is always less than a version without a pre-release when the major, minor,
     *   and patch versions are equal.
     *
     * Example comparisons:
     * - "1.0.0" > "0.9.9"
     * - "1.0.0-alpha" < "1.0.0"
     * - "1.0.0-alpha" < "1.0.0-beta"
     *
     * @param version1 The first semantic version string.
     * @param version2 The second semantic version string.
     * @return True if version1 is greater than version2, otherwise false.
     */
    fun isVersionGreaterThan(version1: String, version2: String): Boolean {
        // Parse versions into main and pre-release parts
        val mainAndPre1 = version1.split("-", limit = 2)
        val mainAndPre2 = version2.split("-", limit = 2)

        val mainParts1 = mainAndPre1[0].split(".").map { it.toInt() }
        val mainParts2 = mainAndPre2[0].split(".").map { it.toInt() }

        // Extend the shorter list with zeros (for "1.2" vs "1.2.3")
        val maxParts = maxOf(mainParts1.size, mainParts2.size)
        val extendedParts1 = mainParts1 + List(maxParts - mainParts1.size) { 0 }
        val extendedParts2 = mainParts2 + List(maxParts - mainParts2.size) { 0 }

        // Compare major, minor, and patch versions
        extendedParts1.zip(extendedParts2).forEach { (v1, v2) ->
            when {
                v1 > v2 -> return true
                v1 < v2 -> return false
            }
        }

        // Handle pre-release versions, which are lower precedence than normal versions
        if (mainAndPre1.size == 2 && mainAndPre2.size == 2) {
            // Both have pre-release versions
            return mainAndPre1[1] > mainAndPre2[1]
        } else if (mainAndPre1.size == 2) {
            // Only version1 has a pre-release version, it's less
            return false
        } else if (mainAndPre2.size == 2) {
            // Only version2 has a pre-release version, it's less
            return true
        }

        // If all numeric parts are equal, and there are no pre-release segments, versions are equal
        return false
    }
}
