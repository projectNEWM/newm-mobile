# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands
- Build project: `./gradlew build`
- Run Android app: `./gradlew :composeapp:installDebug`
- Run Desktop app: `./gradlew :composeapp:run`
- Run Desktop distributable: `./gradlew :composeapp:runDistributable`
- Run Web app (dev): `./gradlew composeapp:wasmJsBrowserDevelopmentRun`
- Run Web app (prod): `./gradlew composeapp:wasmJsBrowserProductionRun`
- Run tests: `./gradlew test`
- Run single test: `./gradlew :module:test --tests "TestClassName.testMethodName"`
- Run Android instrumentation tests: `./gradlew connectedAndroidTest`
- Run snapshot tests: `./gradlew verifyPaparazziDebug`
- Check dependency updates: `./gradlew dependencyUpdates`

## Code Style Guidelines
- Kotlin style: Follow standard Kotlin conventions (camelCase, PascalCase)
- Architecture: KMP (Kotlin Multiplatform) supporting Android, iOS, Desktop (JVM), and Web (WebAssembly)
- Android UI: Uses Circuit library for UI navigation and state management
- Desktop: JVM-based Compose Multiplatform application
- Web: WebAssembly-based Compose Multiplatform application using browser APIs
- Imports: Group by package, alphabetize within groups
- Types: Prefer non-nullable types when possible
- Naming: Descriptive, explicit naming (e.g., `UserRepository` not `Repository`)
- Error handling: Use Result pattern with sealed classes for domain errors
- Dependency injection: Uses Koin for KMP code
- JVM target: 11