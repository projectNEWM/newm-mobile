package io.newm.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.R
import io.newm.core.theme.*
import io.newm.core.ui.text.TextFieldWithLabel
import io.newm.core.ui.text.formTitleStyle

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
            onValueChange = { value -> onProfileUpdated(profile.copy(firstName = value)) },
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_last_name,
            value = profile.lastName,
            onValueChange = { value -> onProfileUpdated(profile.copy(lastName = value)) },
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_email,
            value = profile.email,
            onValueChange = { },
            enabled = false
        )
        Spacer(modifier = Modifier.height(40.dp))
        Divider(
            thickness = 2.dp,
            color = Gray600
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(id = R.string.profile_form_password_title),
            style = formTitleStyle
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_password_current_password,
            onValueChange = { value -> onProfileUpdated(profile.copy(currentPassword = value)) },
            isPassword = true,
            placeholderResId = R.string.profile_placeholder_your_password
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_password_new_password,
            onValueChange = { value -> onProfileUpdated(profile.copy(newPassword = value)) },
            isPassword = true,
            placeholderResId = R.string.profile_placeholder_new_password
        )
        TextFieldWithLabel(
            labelResId = R.string.profile_form_password_confirm_password,
            onValueChange = { value -> onProfileUpdated(profile.copy(confirmPassword = value)) },
            isPassword = true,
            placeholderResId = R.string.profile_placeholder_new_password
        )
        Spacer(modifier = Modifier.height(80.dp))
    }
}

