package yb.kompose.recipetoshoppinglist.features.core.presentation.components.button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import yb.kompose.recipetoshoppinglist.features.core.presentation.models.DesignIconButtonStyle

@Composable
fun DesignIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String,
    style: DesignIconButtonStyle,
    customColors: IconButtonColors? = null,
    enabled: Boolean = true
) {
    val colors = when (style) {
        DesignIconButtonStyle.PRIMARY -> IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiary,
            disabledContentColor = MaterialTheme.colorScheme.onTertiary
        )

        DesignIconButtonStyle.WHITE -> IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.tertiary,
            disabledContentColor = MaterialTheme.colorScheme.onTertiary
        )

        DesignIconButtonStyle.CUSTOM -> customColors
            ?: throw IllegalArgumentException("Style CUSTOM without customColors argument.")
    }

    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = colors,
        enabled = enabled
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}