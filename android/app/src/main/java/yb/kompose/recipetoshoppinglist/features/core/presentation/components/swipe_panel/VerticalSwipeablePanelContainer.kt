package yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.rememberSwipeableState
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.core.presentation.util.dpToPx
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun VerticalSwipeablePanel(
    modifier: Modifier = Modifier,
    behindColor: Color = MaterialTheme.colorScheme.surface,
    behindContentColor: Color = MaterialTheme.colorScheme.onSurface,
    panelColor: Color = MaterialTheme.colorScheme.primary,
    panelContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    collapsePanelHeight: Dp = 120.dp,
    contentBehind: @Composable BoxScope.() -> Unit,
    panelBody: @Composable BoxScope.() -> Unit
) {
    @Suppress("LocalVariableName")
    val SWIPEABLE_PANEL_MAX_CORNER_ANGLE = 50

    val coroutineScope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(0)

    var extendedHeightPx by remember { mutableFloatStateOf(0f) }

    val collapseHeightPx = collapsePanelHeight.dpToPx()

    var anchors by remember { mutableStateOf(mapOf(0f to 0)) }

    var cornerAngle: Dp by remember { mutableStateOf(SWIPEABLE_PANEL_MAX_CORNER_ANGLE.dp) }

    LaunchedEffect(swipeableState.offset.value) {
        val offset = swipeableState.offset.value
        val angle =
            ((offset * SWIPEABLE_PANEL_MAX_CORNER_ANGLE) / (extendedHeightPx * 0.9f)).roundToInt()
        if (offset == 0f && angle == 0) return@LaunchedEffect
        cornerAngle = when (angle) {
            in SWIPEABLE_PANEL_MAX_CORNER_ANGLE..Int.MAX_VALUE -> {
                SWIPEABLE_PANEL_MAX_CORNER_ANGLE.dp
            }

            in 0 until SWIPEABLE_PANEL_MAX_CORNER_ANGLE -> angle.dp
            else -> 0.dp
        }
    }

    LaunchedEffect(extendedHeightPx) {
        if (extendedHeightPx == 0f) return@LaunchedEffect
        anchors = mapOf(extendedHeightPx - collapseHeightPx to 0, 0f to 1)
        // First anchor = Collapsed panel, Second = Extended panel
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
        AnimatedVisibility(
            visible = anchors.size >= 2,
            enter = slideInVertically(
                initialOffsetY = { extendedHeightPx.roundToInt() },
                animationSpec = tween(
                    durationMillis = 1500
                )
            )
        ) {
            VerticalSwipeablePanel(
                swipeableState = swipeableState,
                anchors = anchors,
                cornerAngle = cornerAngle,
                panelColor = panelColor,
                collapsePanelHeight = collapsePanelHeight,
                animateTo = { animatePanelTo(it) }
            ) {
                panelBody()
            }
        }
    }
}

