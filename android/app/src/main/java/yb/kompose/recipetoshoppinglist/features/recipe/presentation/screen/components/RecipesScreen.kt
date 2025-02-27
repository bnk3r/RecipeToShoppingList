package yb.kompose.recipetoshoppinglist.features.recipe.presentation.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.components.CategoriesRow
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screen.models.RecipesScreenState
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.search.components.RecipeSearchBarSection

@Composable
fun RecipesScreen(
    state: RecipesScreenState,
    onRecipeSearchQueryChanged: (String) -> Unit,
    onSelectedCategoryChanged: (UiCategory) -> Unit,
    onClickRecipe: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val recipeItemSize = configuration.screenWidthDp.dp / 3

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
    ) {
        // SEARCH BAR (+ filters)
        item(
            span = { GridItemSpan(3) }
        ) {
            RecipeSearchBarSection(
                query = state.searchQuery,
                onQueryChange = onRecipeSearchQueryChanged,
                onSearch = onRecipeSearchQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        // CATEGORIES
        item(
            span = { GridItemSpan(3) }
        ) {
            CategoriesRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                categories = state.categories,
                selectedCategory = state.selectedCategory,
                onCategorySelected = onSelectedCategoryChanged
            )
        }

        if (state.areRecipesLoading) {
            item(
                span = { GridItemSpan(3) }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            state.recipes?.let { recipes ->
                items(recipes) { recipe ->
                    CachedAsyncImage(
                        modifier = Modifier
                            .size(recipeItemSize)
                            .padding(16.dp)
                            .clickable {
                                onClickRecipe(recipe.id)
                            },
                        url = recipe.imgUrl,
                        title = recipe.title
                    )
                }
            }
        }
    }
}