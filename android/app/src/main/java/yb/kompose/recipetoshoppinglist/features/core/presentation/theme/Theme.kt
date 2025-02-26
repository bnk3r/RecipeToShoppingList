package yb.kompose.recipetoshoppinglist.features.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = darkColorScheme(
    primary = OrangeDark,
    onPrimary = White,
    secondary = GreenDark,
    onSecondary = White,
    tertiary = GrayLight,
    onTertiary = GrayMedium,
    error = RedLight,
    onError = White,
    surface = White,
    onSurface = Black,
    background = White,
    onBackground = Black
)

@Composable
fun RecipeToShoppingListTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}