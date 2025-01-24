package yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.FrenchTranslatedText
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory

@Composable
fun RowCategory(
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
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.imageUrl)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = category.name,
            contentScale = ContentScale.Crop,
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
                )
        )
        FrenchTranslatedText(
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