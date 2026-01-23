package io.newm.sharedfeatures.ui.text

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.sharedfeatures.core.resources.Res
import io.newm.sharedfeatures.core.resources.ic_search
import io.newm.sharedfeatures.theme.Gray23
import io.newm.sharedfeatures.theme.GraySuit
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchBar(
    placeholderResId: StringResource,
    iconResId: DrawableResource = Res.drawable.ic_search,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (text, onValueChange) = remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { query ->
            onValueChange(query)
            onQueryChange(query)
        },
        modifier = modifier,
        textStyle =
            TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
            ),
        placeholder = { Text(text = stringResource(placeholderResId)) },
        leadingIcon = { Icon(painter = painterResource(iconResId), contentDescription = null) },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ), // This is to remove the underline when typing
        shape = RoundedCornerShape(8.dp),
        colors =
            TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Gray23,
                unfocusedBorderColor = Gray23,
                backgroundColor = Gray23,
                placeholderColor = GraySuit,
                textColor = GraySuit,
            ),
    )
}
