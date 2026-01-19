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

include(":android:core:android:implementations")

include(":android:core:resources")

include(":android:core:test-utils")

include(":android:core:theme")

include(":android:core:ui-utils")

include(":android:features:barcode-scanner")

include(":android:features:login")

include(":android:features:music-player")

include(":composeapp")

include(":shared")

include(":sharedfeatures")
