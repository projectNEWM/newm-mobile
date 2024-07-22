package io.newm.screens.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.newm.core.ui.text.formEmailStyle
import io.newm.core.ui.text.formNameStyle

@Composable
fun ProfileHeader(
    firstName: String,
    lastName: String,
    email: String
) {
    val fullName = if(firstName.isNotEmpty() || lastName.isNotEmpty()) "$firstName $lastName" else ""

    Text(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        text = fullName.ifEmpty { "Welcome to NEWM" },
        style = formNameStyle
    )
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally),
        text = email,
        style = formEmailStyle
    )
}
