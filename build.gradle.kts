import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import java.util.Locale

buildscript { Repo.addRepos(repositories) }

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.plugin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.gradleVersions)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    afterEvaluate {
        project.extensions
            .findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()
            .let { kmpExt ->
                kmpExt?.sourceSets?.removeAll {
                    setOf(
                            "androidAndroidTestRelease",
                            "androidTestFixtures",
                            "androidTestFixturesDebug",
                            "androidTestFixturesRelease",
                        )
                        .contains(it.name)
                }
            }
    }
}

/**
 * Run with `./gradlew dependencyUpdates` and the report will be in:
 * /build/dependencyUpdates/versionsReport.html
 */
fun isNonStable(version: String): Boolean {
    val stableKeyword =
        listOf("RELEASE", "FINAL", "GA").any { version.uppercase(Locale.getDefault()).contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }
    checkForGradleUpdate = true
    outputFormatter = "html"
    reportfileName = "versionsReport"
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    lineEndings = com.diffplug.spotless.LineEnding.UNIX
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")
        ktfmt(libs.versions.ktfmt.get()).kotlinlangStyle()
        ktlint(libs.versions.ktlint.get())
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlinGradle {
        target("**/*.gradle.kts", "*.gradle.kts")
        targetExclude("**/build/**/*.gradle.kts")
        ktfmt(libs.versions.ktfmt.get()).kotlinlangStyle()
        trimTrailingWhitespace()
        endWithNewline()
    }
}
