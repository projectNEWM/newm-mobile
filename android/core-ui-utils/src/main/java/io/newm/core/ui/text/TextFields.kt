package io.newm.core.ui.text

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.theme.*

val formTitleStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Bold,
    color = White
)

val formLabelStyle = TextStyle(
    fontSize = 12.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Bold,
    color = Gray100
)

val formTextFieldStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Normal,
)

@Composable
fun TextFieldWithLabel(
    labelResId: Int,
    value: String? = null,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    enabled: Boolean = true,
    placeholderResId: Int? = null
) {
    var updatedValue by remember { mutableStateOf(value.orEmpty()) }
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = stringResource(id = labelResId),
        style = formLabelStyle
    )
    Spacer(modifier = Modifier.height(4.dp))
    OutlinedTextField(
        value = updatedValue,
        onValueChange = {
            updatedValue = it
            onValueChange(it)
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        textStyle = formTextFieldStyle,
        colors =
        TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Gray500,
            unfocusedBorderColor = Gray500,
            backgroundColor = if (enabled) Gray600 else Gray650,
            textColor = if (enabled) White else White50
        ),
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        enabled = enabled,
        placeholder = placeholderResId?.let { { Text(text = stringResource(id = it)) } }
    )
}