import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    Repo.addRepos(repositories)

    dependencies {
        classpath(Build.gradle)
        classpath(Google.crashlyticsClasspath)
        classpath(Google.googleServices)
        classpath(Kotlin.kotlinGradle)
        classpath(Kotlin.serialization)
        classpath(Plugins.benManesVersionsClasspath)
        classpath(SqlDelight.gradlePlugin)
        classpath(Plugins.shotClassPath)
        classpath(Plugins.paparazziClassPath)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
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
