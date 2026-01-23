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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.newm.sharedfeatures.core.resources.Res
import io.newm.sharedfeatures.core.resources.profile_form_email
import io.newm.sharedfeatures.core.resources.profile_form_first_name
import io.newm.sharedfeatures.core.resources.profile_form_last_name
import io.newm.sharedfeatures.core.resources.profile_form_password_confirm_password
import io.newm.sharedfeatures.core.resources.profile_form_password_current_password
import io.newm.sharedfeatures.core.resources.profile_form_password_new_password
import io.newm.sharedfeatures.core.resources.profile_form_password_title
import io.newm.sharedfeatures.screens.auth.login.Password
import io.newm.sharedfeatures.screens.auth.login.PasswordState
import io.newm.sharedfeatures.screens.auth.login.TextFieldState
import io.newm.sharedfeatures.theme.Gray16
import io.newm.sharedfeatures.theme.Gray23
import io.newm.sharedfeatures.ui.text.TextFieldWithLabel
import io.newm.sharedfeatures.ui.text.formTitleStyle
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileForm(
    email: String,
    canUserEditName: Boolean,
    firstName: TextFieldState,
    lastName: TextFieldState,
    currentPasswordState: TextFieldState,
    newPasswordState: TextFieldState,
    confirmNewPasswordState: TextFieldState,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Gray16)) {
            Column(modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp)) {
                if (canUserEditName) {
                    TextFieldWithLabel(
                        labelResId = Res.string.profile_form_first_name,
                        value = firstName.text,
                        onValueChange = { firstName.text = it },
                        textfieldBackgroundColor = Gray16,
                    )
                    TextFieldWithLabel(
                        labelResId = Res.string.profile_form_last_name,
                        value = lastName.text,
                        onValueChange = { lastName.text = it },
                        textfieldBackgroundColor = Gray16,
                    )
                } else {
                    TextFieldWithLabel(
                        labelResId = Res.string.profile_form_first_name,
                        value = firstName.text,
                        onValueChange = {},
                        enabled = false,
                        textfieldBackgroundColor = Gray23,
                    )
                    TextFieldWithLabel(
                        labelResId = Res.string.profile_form_last_name,
                        value = lastName.text,
                        onValueChange = {},
                        enabled = false,
                        textfieldBackgroundColor = Gray23,
                    )
                }
                TextFieldWithLabel(
                    labelResId = Res.string.profile_form_email,
                    value = email,
                    onValueChange = {},
                    enabled = false,
                    textfieldBackgroundColor = Gray23,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Gray16)) {
            Column(modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp)) {
                Text(
                    text = stringResource(Res.string.profile_form_password_title),
                    style = formTitleStyle,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Password(
                    label = Res.string.profile_form_password_current_password,
                    passwordState = currentPasswordState as PasswordState,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
                Password(
                    label = Res.string.profile_form_password_new_password,
                    passwordState = newPasswordState as PasswordState,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
                Password(
                    label = Res.string.profile_form_password_confirm_password,
                    passwordState = confirmNewPasswordState as PasswordState,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )
            }
        }
    }
}
