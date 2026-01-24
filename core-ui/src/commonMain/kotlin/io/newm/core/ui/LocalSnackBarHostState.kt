package io.newm.core.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackBarHostState =
    compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState provided") }
