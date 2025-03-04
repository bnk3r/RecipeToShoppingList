package yb.kompose.recipetoshoppinglist.features.core.presentation.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DesignOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    imageVector: ImageVector? = null
) {
    val border = when (enabled) {
        true -> BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        false -> BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
    }
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.tertiary
        ),
        border = border,
        enabled = enabled
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            imageVector?.let { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Text(
                text = text,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun DesignOutlinedButtonPreview() {
    DesignOutlinedButton(
        onClick = {},
        text = "Design Outlined Button",
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun DesignOutlinedButtonWithIconPreview() {
    DesignOutlinedButton(
        onClick = {},
        text = "Design Outlined Button With Icon",
        imageVector = Icons.Default.Home
    )
}