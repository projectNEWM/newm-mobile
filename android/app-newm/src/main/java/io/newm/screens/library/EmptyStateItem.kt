package io.newm.screens.library

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.Gray100
import io.newm.core.theme.Gray600
import io.newm.core.theme.inter

@Composable
fun EmptyStateItem(modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = stringResource(id = R.string.library_empty_state_label),
        onValueChange = {},
        textStyle = TextStyle(
            fontSize = 12.sp,
            fontFamily = inter,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Gray600,
            unfocusedBorderColor = Gray600,
            backgroundColor = Gray600,
            textColor = Gray100
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        shape = RoundedCornerShape(8.dp),
        readOnly = true
    )
}