package yb.kompose.recipetoshoppinglist.features.recipe.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.models.states.RecipesScreenState

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

    val interactionSource = remember { MutableInteractionSource() }
    var hideKeyboard by remember { mutableStateOf(false) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                hideKeyboard = true
            }
    ) {
        // SEARCH BAR (+ filters)
        item(
            span = { GridItemSpan(3) }
        ) {
            RecipeSearchBarSection(
                query = state.searchQuery,
                onQueryChange = onRecipeSearchQueryChanged,
                onSearch = onRecipeSearchQueryChanged,
                hideKeyboard = hideKeyboard,
                onHideKeyboard = { hideKeyboard = it },
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
                    CircularProgressIndicator(
                        indicatorColor = MaterialTheme.colorScheme.primary
                    )
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