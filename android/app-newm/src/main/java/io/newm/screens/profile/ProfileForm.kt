package io.newm.screens.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.R
import io.newm.core.theme.Gray100
import io.newm.core.theme.Gray500
import io.newm.core.theme.White
import io.newm.core.theme.inter

private val formTitleStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Bold,
    color = White
)

private val formLabelStyle = TextStyle(
    fontSize = 12.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Bold,
    color = Gray100
)

private val formTextFieldStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Normal,
    color = Gray100
)

@Composable
fun ProfileForm(
    profile: ProfileModel,
    onProfileUpdated: (ProfileModel) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.profile_form_title),
            style = formTitleStyle
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_first_name,
            value = profile.firstName,
            onValueChange = { value -> onProfileUpdated(profile.copy(firstName = value)) }
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_last_name,
            value = profile.lastName,
            onValueChange = { value -> onProfileUpdated(profile.copy(lastName = value)) }
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_email,
            value = profile.email,
            onValueChange = { value -> onProfileUpdated(profile.copy(email = value)) }
        )
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = stringResource(id = R.string.profile_form_password_title),
            style = formTitleStyle
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_password_current_password,
            onValueChange = { value -> onProfileUpdated(profile.copy(currentPassword = value)) },
            isPassword = true
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_password_new_password,
            onValueChange = { value -> onProfileUpdated(profile.copy(newPassword = value)) },
            isPassword = true
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_password_confirm_password,
            onValueChange = { value -> onProfileUpdated(profile.copy(confirmPassword = value)) },
            isPassword = true
        )
    }
}

@Composable
private fun TextFieldWithLabel(
    labelResId: Int,
    value: String? = null,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    var updatedValue by remember { mutableStateOf(value.orEmpty()) }
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = stringResource(id = labelResId),
        style = formLabelStyle
    )
    Spacer(modifier = Modifier.height(4.dp))
    TextField(
        value = updatedValue,
        onValueChange = {
            updatedValue = it
            onValueChange(it)
        },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        textStyle = formTextFieldStyle,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = Gray500)
    )
}