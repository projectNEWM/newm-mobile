import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import java.util.Locale

buildscript {
    Repo.addRepos(repositories)

    dependencies {
        classpath(libs.gradle)
        classpath(libs.google.services)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.kotlin.serialization)
        classpath(libs.gradle.versions.plugin)
        classpath(libs.sqldelight.gradle.plugin)
        classpath(libs.paparazzi.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.compose.compiler) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    afterEvaluate {
        project.extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()
            .let { kmpExt ->
                kmpExt?.sourceSets?.removeAll {
                    setOf(
                        "androidAndroidTestRelease",
                        "androidTestFixtures",
                        "androidTestFixturesDebug",
                        "androidTestFixturesRelease",
                    ).contains(it.name)
                }
            }
    }
}

/**
 * Run with `./gradlew dependencyUpdates` and the report will be in:
 *      /build/dependencyUpdates/versionsReport.html
 */
apply(plugin = "com.github.ben-manes.versions")

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
        version.uppercase(Locale.getDefault())
            .contains(it)
    }
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

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
