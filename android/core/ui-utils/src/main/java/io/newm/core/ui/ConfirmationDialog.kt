package io.newm.core.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import io.newm.core.resources.R

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    isOpen: MutableState<Boolean>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    positiveButtonColor: Color = MaterialTheme.colors.primary,
    negativeButtonColor: Color = MaterialTheme.colors.background,
) {
    val confirmationText = stringResource(id = R.string.dialog_confirm)
    val cancelText = stringResource(id = R.string.dialog_cancel)
    if (isOpen.value) {
        AlertDialog(
            onDismissRequest = {
                // Update the isOpen state to false when the user clicks outside the dialog or presses the back button
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = positiveButtonColor)
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = negativeButtonColor)
                ) {
                    Text(cancelText)
                }
            }
        )
    }
}
