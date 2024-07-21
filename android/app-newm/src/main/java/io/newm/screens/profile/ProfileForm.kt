package io.newm.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.core.theme.Gray16
import io.newm.core.theme.Gray23
import io.newm.core.ui.text.TextFieldWithLabel
import io.newm.core.ui.text.formTitleStyle
import io.newm.feature.login.screen.TextFieldState
import io.newm.feature.login.screen.email.Email
import io.newm.feature.login.screen.password.Password

@Composable
fun ProfileForm(
    email: String,
    firstName: TextFieldState,
    lastName: TextFieldState,
    currentPasswordState: TextFieldState,
    newPasswordState: TextFieldState,
    confirmNewPasswordState: TextFieldState,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Gray16)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            ) {
                Email(
                    label = R.string.profile_form_first_name,
                    emailState = firstName,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    )
                )
                Email(
                    label = R.string.profile_form_last_name,
                    emailState = lastName,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    )
                )
                TextFieldWithLabel(
                    labelResId = R.string.profile_form_email,
                    value = email,
                    onValueChange = { },
                    enabled = false,
                    textfieldBackgroundColor = Gray23,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Gray16)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.profile_form_password_title),
                    style = formTitleStyle
                )
                Spacer(modifier = Modifier.height(16.dp))
                Password(
                    label = R.string.profile_form_password_current_password,
                    passwordState = currentPasswordState,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    )
                )
                Password(
                    label = R.string.profile_form_password_new_password,
                    passwordState = newPasswordState,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    )
                )
                Password(
                    label = R.string.profile_form_password_confirm_password,
                    passwordState = confirmNewPasswordState,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    )
                )
            }
        }
    }
}

