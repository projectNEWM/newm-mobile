package io.newm.sharedfeatures.screens.auth.resetpassword

import com.slack.circuit.runtime.screen.Screen
import io.newm.sharedfeatures.parceling.CommonParcelize

@CommonParcelize data class ResetPasswordScreen(
    val email: String,
) : Screen
