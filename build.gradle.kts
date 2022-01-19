buildscript {
    Repo.addRepos(repositories)

    dependencies {
        classpath(Build.gradle)
        classpath(Build.gradleVersions)
        classpath(Hilt.hiltPlugin)
        classpath(Kotlin.kotlinGradle)
        classpath(Kotlin.serialization)
        classpath(SqlDelight.gradlePlugin)
    }
}

apply(plugin = Plugins.versions)

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}