package yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.SwipeableState
import androidx.wear.compose.material.swipeable
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun VerticalSwipeablePanel(
    swipeableState: SwipeableState<Int>,
    anchors: Map<Float, Int>,
    cornerAngle: Dp,
    panelColor: Color,
    collapsePanelHeight: Dp,
    animateTo: (Int) -> Unit,
    panelBody: @Composable BoxScope.() -> Unit
) {
    var headerCollapseVisible by remember { mutableStateOf(true) }

    LaunchedEffect(swipeableState.currentValue) {
        when (swipeableState.currentValue) {
            0 -> headerCollapseVisible = true
            1 -> headerCollapseVisible = false
        }
    }

    Box(
        modifier = Modifier
            .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
            // modifiers must be placed after .offset !!
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Vertical
            )
            .clip(
                RoundedCornerShape(
                    topStart = cornerAngle,
                    topEnd = cornerAngle,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(panelColor)
    ) {
        when (swipeableState.currentValue) {
            0 -> {
                VerticalSwipeablePanelCollapsedHeader(
                    visible = headerCollapseVisible,
                    height = collapsePanelHeight,
                    onClick = {
                        animateTo(1)
                        headerCollapseVisible = !headerCollapseVisible
                    }
                )
            }

            1 -> {
                VerticalSwipeablePanelExtendedHeader(
                    height = collapsePanelHeight,
                    onClick = {
                        animateTo(0)
                        headerCollapseVisible = !headerCollapseVisible
                    },
                    visible = !headerCollapseVisible
                )
            }

        }

        panelBody()
    }
}