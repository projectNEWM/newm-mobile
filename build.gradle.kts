buildscript {
    Repo.addRepos(repositories)

    dependencies {
        classpath(Deps.gradlePlugin)
        classpath(Deps.kotlinGradlePlugin)
        classpath(Deps.gradleVersionsPlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

apply(plugin = "com.github.ben-manes.versions")

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}