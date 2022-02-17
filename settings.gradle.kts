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
include(":androidApps:DemoApp")
include(":androidApps:ProjectNewm")
include(":androidCore:core-theme")
include(":androidFeatures:feature-example")
include(":shared")
