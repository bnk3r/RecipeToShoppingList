package yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.core.presentation.util.dpToPx
import kotlin.math.roundToInt

const val SWIPEABLE_PANEL_MAX_CORNER_ANGLE = 50

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun VerticalSwipeablePanel(
    modifier: Modifier = Modifier,
    behindColor: Color = MaterialTheme.colorScheme.surface,
    panelColor: Color = MaterialTheme.colorScheme.primary,
    collapsePanelHeight: Dp = 120.dp,
    contentBehind: @Composable BoxScope.() -> Unit,
    panelBody: @Composable BoxScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(0)

    var extendedHeightPx by remember { mutableFloatStateOf(0f) }

    val collapseHeightPx = collapsePanelHeight.dpToPx()

    var anchors by remember { mutableStateOf(mapOf(0f to 0)) }

    var cornerAngle: Dp by remember { mutableStateOf(SWIPEABLE_PANEL_MAX_CORNER_ANGLE.dp) }

    var headerCollapseVisible by remember { mutableStateOf(true) }

    LaunchedEffect(swipeableState.offset.value) {
        val offset = swipeableState.offset.value
        val angle = ((offset * SWIPEABLE_PANEL_MAX_CORNER_ANGLE) / (extendedHeightPx * 0.9f)).roundToInt()
        if (offset == 0f && angle == 0) return@LaunchedEffect
        cornerAngle = when (angle) {
            in SWIPEABLE_PANEL_MAX_CORNER_ANGLE..Int.MAX_VALUE -> {
                SWIPEABLE_PANEL_MAX_CORNER_ANGLE.dp
            }
            in Int.MIN_VALUE until 1 -> {
                0.dp
            }
            else -> {
                angle.dp
            }
        }
    }

    LaunchedEffect(extendedHeightPx) {
        if (extendedHeightPx == 0f) return@LaunchedEffect
        anchors = mapOf(extendedHeightPx - collapseHeightPx to 0, 0f to 1)
        // First anchor = Collapsed panel, Second = Extended panel
    }

    LaunchedEffect(swipeableState.currentValue) {
        when (swipeableState.currentValue) {
            0 -> headerCollapseVisible = true
            1 -> headerCollapseVisible = false
        }
    }

    fun animatePanelTo(target: Int) = coroutineScope.launch {
        swipeableState.animateTo(target)
    }

    Box(
        modifier = modifier
            .onGloballyPositioned { extendedHeightPx = it.size.height.toFloat() }
            .clip(RectangleShape)
            .background(behindColor)
    ) {
        contentBehind()
        if (anchors.size > 1) {
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
                                animatePanelTo(1)
                                headerCollapseVisible = !headerCollapseVisible
                            }
                        )
                    }

                    1 -> {
                        VerticalSwipeablePanelExtendedHeader(
                            height = collapsePanelHeight,
                            onClick = {
                                animatePanelTo(0)
                                headerCollapseVisible = !headerCollapseVisible
                            },
                            visible = !headerCollapseVisible
                        )
                    }

                }

                panelBody()
            }
        }
    }
}

