object Circuit {
    // The circuit foundational APIs like CircuitConfig, CircuitContent, etc. Depends on the other three.
    const val foundation = "com.slack.circuit:circuit-foundation:${Versions.circuit}"
    // Common runtime components like Screen, Navigator, etc.
    const val runtime = "com.slack.circuit:circuit-runtime:${Versions.circuit}"
    // The Presenter API, depends on circuit-runtime.
    const val runtimePresenter = "com.slack.circuit:circuit-runtime-presenter:${Versions.circuit}"
    // The Ui API, depends on circuit-runtime.
    const val runtimeUi = "com.slack.circuit:circuit-runtime-ui:${Versions.circuit}"
    // This is be used to remember values that survive configuration changes using rememberRetained{}
    const val retained = "com.slack.circuit:circuit-retained:${Versions.circuit}"
}
