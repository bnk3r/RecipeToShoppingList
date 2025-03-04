package yb.kompose.recipetoshoppinglist.features.core.presentation.components.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.core.presentation.models.FrontLayerPosition

@Composable
fun FrontLayer(
    position: FrontLayerPosition,
    padding: Dp = 16.dp,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = when (position) {
            FrontLayerPosition.TOP_START, FrontLayerPosition.TOP_END -> Arrangement.Top
            FrontLayerPosition.BOTTOM_START, FrontLayerPosition.BOTTOM_END -> Arrangement.Bottom
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = when (position) {
                FrontLayerPosition.TOP_START, FrontLayerPosition.BOTTOM_START -> Arrangement.Start
                FrontLayerPosition.TOP_END, FrontLayerPosition.BOTTOM_END -> Arrangement.End
            }
        ) {
            content()
        }
    }
}