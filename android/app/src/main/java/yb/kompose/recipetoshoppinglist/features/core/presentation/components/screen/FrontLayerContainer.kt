package yb.kompose.recipetoshoppinglist.features.core.presentation.components.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.core.presentation.models.FrontLayerPosition

@Composable
fun FrontLayerContainer(
    content: @Composable () -> Unit,
    frontLayerContent: @Composable () -> Unit,
    frontLayerContentPosition: FrontLayerPosition,
    frontLayerPadding: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        content()
        FrontLayer(
            position = frontLayerContentPosition,
            padding = frontLayerPadding
        ) {
            frontLayerContent()
        }
    }
}