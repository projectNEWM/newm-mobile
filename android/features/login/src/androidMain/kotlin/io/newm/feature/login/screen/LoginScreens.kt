package io.newm.feature.login.screen

import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

// TODO move screens to a common module
@Parcelize object LoginScreen : Screen
@Parcelize object HomeScreen : Screen

@Parcelize data class ResetPasswordScreen(val email: String) : Screen
