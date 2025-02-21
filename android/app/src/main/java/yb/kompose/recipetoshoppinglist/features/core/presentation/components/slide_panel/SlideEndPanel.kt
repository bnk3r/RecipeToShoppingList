package yb.kompose.recipetoshoppinglist.features.core.presentation.components.slide_panel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.core.presentation.util.dpToPx
import kotlin.math.roundToInt

@Composable
fun SlideEndPanel(
    modifier: Modifier = Modifier,
    visible: Boolean,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val widthPx = configuration.screenWidthDp.dp.dpToPx().roundToInt()

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(initialOffsetX = { widthPx }),
        exit = slideOutHorizontally(targetOffsetX = { widthPx }),
        modifier = modifier
    ) {
        content()
    }
}