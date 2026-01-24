package io.newm.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import io.newm.core.resources.Res
import io.newm.core.resources.dialog_cancel
import io.newm.core.resources.dialog_confirm
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    isOpen: MutableState<Boolean>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    positiveButtonColor: Color = MaterialTheme.colorScheme.primary,
    negativeButtonColor: Color = MaterialTheme.colorScheme.background,
) {
    val confirmationText = stringResource(Res.string.dialog_confirm)
    val cancelText = stringResource(Res.string.dialog_cancel)
    if (isOpen.value) {
        AlertDialog(
            onDismissRequest = {
                // Update the isOpen state to false when the user clicks outside the dialog or
                // presses the
                // back button
                isOpen.value = false
                onDismiss() // Call the onDismiss lambda to handle any additional logic
            },
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                Button(
                    onClick = {
                        isOpen.value = false // Close the dialog
                        onConfirm() // Handle the confirm action
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = positiveButtonColor),
                ) {
                    Text(confirmationText)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isOpen.value = false // Close the dialog
                        onDismiss() // Handle the dismiss action
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = negativeButtonColor),
                ) {
                    Text(cancelText)
                }
            },
        )
    }
}