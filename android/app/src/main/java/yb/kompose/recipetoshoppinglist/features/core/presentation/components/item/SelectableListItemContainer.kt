package yb.kompose.recipetoshoppinglist.features.core.presentation.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SelectableListItemContainer(
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .background(
                when {
                    selected -> MaterialTheme.colorScheme.secondary
                    else -> Color.Transparent
                }
            )
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectableListItemContainerPreview() {
    SelectableListItemContainer(
        onClick = {},
        selected = true,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Selected", modifier = Modifier.fillMaxWidth())
    }
}