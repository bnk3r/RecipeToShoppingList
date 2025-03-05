package yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.SwipeableState
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.RedLight
import yb.kompose.recipetoshoppinglist.features.core.presentation.util.dpToPx
import yb.kompose.recipetoshoppinglist.features.core.presentation.util.pxToDp
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun HorizontalSwipeablePanel(
    modifier: Modifier = Modifier,
    height: Dp = 120.dp,
    frontContent: @Composable () -> Unit,
    backContent: @Composable (SwipeableState<Int>) -> Unit,
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val backContainerWidth = LocalConfiguration.current.screenWidthDp.dp.dpToPx() / 5f

    Box(
        modifier = modifier
    ) {
        // BACK
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(8.dp)),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(backContainerWidth.roundToInt().pxToDp())
                    .background(RedLight)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(backContainerWidth.roundToInt().pxToDp())
                    .background(RedLight)
            ) {
                backContent(swipeableState)
            }
        }
        // FRONT
        HorizontalSwipeablePanelContent(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .clip(RoundedCornerShape(8.dp))
                .swipeable(
                    state = swipeableState,
                    anchors = mapOf(0f to 0, backContainerWidth * -1f to 1),
                    orientation = Orientation.Horizontal,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) }
                )
        ) {
            frontContent()
        }
    }

}

