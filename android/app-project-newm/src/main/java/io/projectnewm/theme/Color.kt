package io.projectnewm.theme

import androidx.compose.ui.graphics.Color
import com.airbnb.android.showkase.annotation.ShowkaseColor

@ShowkaseColor(name = "Primary", group = ColorGroups.darkColors)
val Purple200 = Color(0xFFBB86FC)

@ShowkaseColor(name = "Primary", group = ColorGroups.lightColors)
val Purple500 = Color(0xFF6200EE)

@ShowkaseColor(name = "Primary Variant", group = ColorGroups.commonColors)
val Purple700 = Color(0xFF3700B3)

@ShowkaseColor(name = "Secondary", group = ColorGroups.commonColors)
val Teal200 = Color(0xFF03DAC5)


private object ColorGroups {
    const val darkColors = "Dark Colors"
    const val lightColors = "Light Colors"
    const val commonColors = "Common Colors"
}