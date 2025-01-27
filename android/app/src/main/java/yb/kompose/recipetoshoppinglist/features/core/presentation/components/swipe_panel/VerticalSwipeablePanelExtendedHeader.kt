package yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSwipeablePanelExtendedHeader(
    height: Dp,
    onClick: () -> Unit,
    visible: Boolean
) {
    AnimatedVisibility(
        visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(CircleShape)
                .clickable { onClick() }
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