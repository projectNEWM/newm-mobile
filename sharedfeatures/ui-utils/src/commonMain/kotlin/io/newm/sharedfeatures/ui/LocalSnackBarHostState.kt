package io.newm.sharedfeatures.ui

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackBarHostState =
    compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState provided") }
