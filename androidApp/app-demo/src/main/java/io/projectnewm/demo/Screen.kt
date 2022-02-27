package io.projectnewm.demo

sealed class Screen(
    val route: String,
) {
    object Example : Screen("example")
}
