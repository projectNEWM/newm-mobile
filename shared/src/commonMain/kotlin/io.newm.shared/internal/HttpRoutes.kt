package io.newm.shared.internal

internal object HttpRoutes {
    fun getHost(): String {
        return if (isProduction) PROD_ENVIRONMENT else DEV_ENVIRONMENT
    }

    private const val DEV_ENVIRONMENT = "https://garage.newm.io"
    private const val PROD_ENVIRONMENT = "https://studio.newm.io"

    private const val isProduction: Boolean = false
}