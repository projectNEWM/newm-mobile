package io.newm.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.newm.core.resources.R
import io.newm.core.ui.buttons.PrimaryButton
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.theme.Black90
import io.newm.core.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfilePictureUpdateSheet(
    sheetState: ModalBottomSheetState,
    onReplacePicture: () -> Unit,
    onRemovePicture: () -> Unit,
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier =
                    Modifier.fillMaxWidth().background(MaterialTheme.colors.surface).padding(16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.title_profile_picture),
                    style =
                        TextStyle(
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = White,
                        ),
                )
                Spacer(modifier = Modifier.height(32.dp))
                PrimaryButton(
                    text = stringResource(id = R.string.profile_replace_picture),
                    onClick = onReplacePicture,
                )
                Spacer(modifier = Modifier.height(16.dp))
                SecondaryButton(
                    label = stringResource(R.string.profile_remove_picture),
                    onClick = onRemovePicture,
                )
            }
        },
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        scrimColor = Black90,
        content = {},
    )
}
