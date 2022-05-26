object Koin {
    private const val VERSION = "3.2.0"

    const val core = "io.insert-koin:koin-core:${VERSION}"
    const val ktor = "io.insert-koin:koin-ktor:${VERSION}"

    const val android = "io.insert-koin:koin-android:${VERSION}"
    const val androidViewModel = "io.insert-koin:koin-androidx-viewmodel:2.2.3"
    const val androidCompose = "io.insert-koin:koin-androidx-compose:${VERSION}"
    const val androidNavigation = "io.insert-koin:koin-androidx-navigation:${VERSION}"

    const val koinTest = "io.insert-koin:koin-test:${VERSION}"
    const val koinJUnit4Test = "io.insert-koin:koin-test-junit4:${VERSION}"
    const val koinJUnit5Test = "io.insert-koin:koin-test-junit5:${VERSION}"
}