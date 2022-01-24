buildscript {
    Repo.addRepos(repositories)

    dependencies {
        classpath(Deps.Plugins.gradle)
        classpath(Deps.Plugins.gradleVersions)
        classpath(Deps.Plugins.kotlinGradle)
        classpath(Deps.Plugins.kotlinSerialization)
        classpath(Deps.Plugins.gradleHilt)
    }
}

apply(plugin = "com.github.ben-manes.versions")

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}