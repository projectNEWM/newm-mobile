package io.newm.core.ui.text

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.theme.Gray300
import io.newm.core.theme.Gray500
import io.newm.core.theme.Gray600
import io.newm.core.theme.inter

@Composable
fun SearchBar(
    placeholderResId: Int,
    iconResId: Int = R.drawable.ic_search,
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
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = inter,
            fontWeight = FontWeight.Normal,
        ),
        placeholder = { Text(text = stringResource(placeholderResId)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), //This is to remove the underline when typing
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Gray500,
            unfocusedBorderColor = Gray500,
            backgroundColor = Gray600,
            placeholderColor = Gray300,
            textColor = Color.White
        )
    )
}
