package yb.kompose.recipetoshoppinglist.features.recipe.presentation.recipes.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest

@Composable
fun RecipeThumbnail(
    modifier: Modifier = Modifier,
    url: String?,
    title: String,
    onClick: () -> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = title,
        contentScale = ContentScale.Crop,
        modifier = modifier.clickable { onClick() }
    )
}