package yb.kompose.recipetoshoppinglist.features.recipe.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory

@Composable
fun SelectableCategory(
    category: UiCategory,
    selected: Boolean?,
    iconSize: Dp = 56.dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        CachedAsyncImage(
            modifier = Modifier
                .size(iconSize)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = when (selected) {
                        true -> 2.dp
                        else -> 0.dp
                    },
                    color = when (selected) {
                        true -> MaterialTheme.colorScheme.primary
                        else -> Color.Transparent
                    },
                    shape = RoundedCornerShape(8.dp)
                ),
            url = category.imageUrl,
            title = category.name
        )
        Text(
            text = category.name,
            textAlign = TextAlign.Center,
            color = when (selected) {
                true -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onSurface
            },
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectableCategoryPreview() {
    val beef = UiCategory("beef", "")
    SelectableCategory(
        category = beef,
        selected = true,
        onClick = {}
    )
}