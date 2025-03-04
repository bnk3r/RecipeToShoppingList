package yb.kompose.recipetoshoppinglist.features.core.presentation.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest

@Composable
fun CachedAsyncImage(
    modifier: Modifier = Modifier,
    url: String?,
    title: String,
    contentScale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = title,
        contentScale = contentScale,
        modifier = modifier,
    )
}