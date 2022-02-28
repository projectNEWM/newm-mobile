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
include(":android:app-demo")
include(":android:app-project-newm")
include(":android:feature-example")
include(":android:core-theme")
include(":shared")
