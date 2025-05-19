package io.newm.screens.wallets.view

import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.newm.core.theme.NewmTheme

@Composable
internal fun WalletsAlertDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    /**
     * TODO get design feedback on this dialog
     */
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Disconnect All Wallets?")
        },
        text = {
            Text(text = "You're about to disconnect all your wallets from NEWM. Are you sure?")
        },
        confirmButton = {
            TextButton(onConfirm) {
                Text(text = "Disconnect")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colors.onPrimary.copy(alpha = ContentAlpha.medium)
                )
            ) {
                Text(text = "Cancel")
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    NewmTheme(darkTheme = true) {
        WalletsAlertDialog({}) { }
    }
}