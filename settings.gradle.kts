dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "newm-mobile"
include(":androidApp:app-demo")
include(":androidApp:app-project-newm")
include(":androidApp:feature-example")
include(":androidApp:core-theme")
include(":shared")
