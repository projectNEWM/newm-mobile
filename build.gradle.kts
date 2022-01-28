import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    Repo.addRepos(repositories)

    dependencies {
        classpath(Build.gradle)
        classpath(Plugins.benManesVersionsClasspath)
        classpath(Hilt.hiltPlugin)
        classpath(Kotlin.kotlinGradle)
        classpath(Kotlin.serialization)
        classpath(SqlDelight.gradlePlugin)
    }
}


/**
 * Run with `./gradlew dependencyUpdates` and the report will be in:
 *      /build/dependencyUpdates/versionsReport.html
 */
apply(plugin = Plugins.benManesVersionsPlugin)

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
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
    delete(rootProject.buildDir)
}