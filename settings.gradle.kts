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
include(":android:app-newm")
include(":android:core-theme")
include(":android:core-ui-utils")
include(":android:core-resources")
include(":android:core-test-utils")
include(":android:feature-now-playing")
include(":android:feature-login")
include(":shared")
