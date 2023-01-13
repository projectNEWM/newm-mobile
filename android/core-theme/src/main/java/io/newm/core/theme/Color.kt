package io.newm.core.theme

import androidx.compose.ui.graphics.Color
import com.airbnb.android.showkase.annotation.ShowkaseColor

@ShowkaseColor(name = "Primary", group = ColorGroups.darkColors)
val NewmWhite = Color(0xFFFFFFFF)

val NewmBlack = Color(0xFF0A0A0A)

@ShowkaseColor(name = "Primary", group = ColorGroups.lightColors)
val Purple500 = Color(0xFF6200EE)

@ShowkaseColor(name = "Primary Variant", group = ColorGroups.commonColors)
val Purple700 = Color(0xFF3700B3)

@ShowkaseColor(name = "Secondary", group = ColorGroups.commonColors)
val NewmYellow = Color(0xFFFF9900)


private object ColorGroups {
    const val darkColors = "Dark Colors"
    const val lightColors = "Light Colors"
    const val commonColors = "Common Colors"
}