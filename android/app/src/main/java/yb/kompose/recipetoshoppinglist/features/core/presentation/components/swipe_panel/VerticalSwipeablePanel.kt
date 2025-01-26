package yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun VerticalSwipeablePanel(
    modifier: Modifier = Modifier,
    panelColor: Color = MaterialTheme.colorScheme.primary,
    collapsePanelHeight: Dp = 96.dp,
    contentBehind: @Composable BoxScope.() -> Unit,
    panelBody: @Composable BoxScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val swipeableState = rememberSwipeableState(0)

    var extendedHeightPx by remember { mutableFloatStateOf(0f) }

    val collapseHeightPx = collapsePanelHeight.dpToPx()

    var anchors by remember { mutableStateOf(mapOf(0f to 0)) }

    val maxCornerAngle = 50

    val cornerAngle: Dp by remember(extendedHeightPx, swipeableState.offset) {
        derivedStateOf {
            if (extendedHeightPx == 0f) return@derivedStateOf 0.dp
            val offset = swipeableState.offset.value
            when (val angle =
                ((offset * maxCornerAngle) / (extendedHeightPx * 0.9f)).roundToInt()) {
                in maxCornerAngle..Int.MAX_VALUE -> maxCornerAngle.dp
                in Int.MIN_VALUE until 0 -> 0.dp
                else -> angle.dp
            }
        }
    }

    LaunchedEffect(extendedHeightPx) {
        if (extendedHeightPx != 0f) {
            anchors = mapOf(
                extendedHeightPx - collapseHeightPx to 0, // First anchor 90% height from top
                0f to 1 // Second anchor top offset 0
            )
        }
    }

    var panelHeaderVisible by remember { mutableStateOf(true) }

    val panelHeaderCloseVisible by remember(panelHeaderVisible) {
        derivedStateOf {
            !panelHeaderVisible
        }
    }

    LaunchedEffect(swipeableState.currentValue) {
        when (swipeableState.currentValue) {
            0 -> panelHeaderVisible = true
            1 -> panelHeaderVisible = false
        }
    }

    fun animatePanelTo(target: Int) = coroutineScope.launch {
        swipeableState.animateTo(target)
    }

    Box(
        modifier = modifier
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Vertical
            )
            .onGloballyPositioned { configuration ->
                extendedHeightPx = configuration.size.height.toFloat()
            }
            .clip(RectangleShape)
    ) {
        contentBehind()
        if (anchors.size > 1) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
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
                        AnimatedVisibility(
                            panelHeaderVisible,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(collapsePanelHeight),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            animatePanelTo(1)
                                        }
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowUp,
                                        contentDescription = null,
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ShoppingBasket,
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }

                            }

                        }
                    }

                    1 -> {
                        AnimatedVisibility(
                            panelHeaderCloseVisible,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(collapsePanelHeight)
                                    .clip(CircleShape)
                                    .clickable {
                                        animatePanelTo(0)
                                    }
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                )
                            }
                        }
                    }

                }

                panelBody()
            }
        }
    }
}