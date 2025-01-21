package yb.kompose.recipetoshoppinglist.features.recipe.presentation.recipes.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe

@Composable
fun RecipesGrid(
    recipes: List<UiRecipe>,
    onRecipeClick: (UiRecipe) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
    ) {
        items(recipes) { recipe ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.imgUrl)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = recipe.title,
                placeholder = painterResource(R.drawable.loading),
                contentScale = ContentScale.Crop
            )
        }
    }
}