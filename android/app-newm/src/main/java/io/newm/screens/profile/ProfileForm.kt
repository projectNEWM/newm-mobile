package io.newm.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.newm.core.resources.R
import io.newm.core.theme.Gray16
import io.newm.core.theme.Gray23
import io.newm.core.ui.text.TextFieldWithLabel
import io.newm.core.ui.text.formTitleStyle
import io.newm.shared.public.models.User

@Composable
fun ProfileForm(
    profile: User,
    onProfileUpdated: (User) -> Unit
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
                TextFieldWithLabel(
                    labelResId = R.string.profile_form_nickname,
                    value = profile.nickname,
                    onValueChange = { value -> onProfileUpdated(profile.copy(nickname = value)) },
                    textfieldBackgroundColor = Gray23,
                )
                TextFieldWithLabel(
                    labelResId = R.string.profile_form_email,
                    value = profile.email,
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
                TextFieldWithLabel(
                    labelResId = R.string.profile_form_password_current_password,
                    onValueChange = { value -> onProfileUpdated(profile.copy(currentPassword = value)) },
                    isPassword = true,
                    textfieldBackgroundColor = Gray23,
                    placeholderResId = R.string.profile_placeholder_your_password
                )
                TextFieldWithLabel(
                    labelResId = R.string.profile_form_password_new_password,
                    onValueChange = { value -> onProfileUpdated(profile.copy(newPassword = value)) },
                    isPassword = true,
                    textfieldBackgroundColor = Gray23,
                    placeholderResId = R.string.profile_placeholder_new_password
                )
                TextFieldWithLabel(
                    labelResId = R.string.profile_form_password_confirm_password,
                    onValueChange = { value -> onProfileUpdated(profile.copy(confirmPassword = value)) },
                    isPassword = true,
                    textfieldBackgroundColor = Gray23,
                    placeholderResId = R.string.profile_placeholder_new_password
                )
            }
        }
    }
}

