package yb.kompose.recipetoshoppinglist.features.core.presentation.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DesignRoundedSquareIconButton(
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    background: Color = MaterialTheme.colorScheme.surface,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String
) {
    Box(
        modifier = modifier
            .size(size)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = iconColor,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Preview
@Composable
private fun DesignRoundedSquareIconButtonPreview() {
    DesignRoundedSquareIconButton(
        onClick = {},
        imageVector = Icons.Default.Add,
        contentDescription = ""
    )
}