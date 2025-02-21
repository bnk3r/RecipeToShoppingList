package yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.components

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.CircularProgressIndicator
import org.koin.androidx.compose.koinViewModel
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.components.CategoriesRow
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos.RecipesPanelViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.search.components.RecipeSearchBarSection

@Composable
fun RecipesPanel(
    viewModel: RecipesPanelViewModel = koinViewModel(),
    showRecipeDetails: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val recipeItemSize = configuration.screenWidthDp.dp / 3

    val categories = viewModel.categories.collectAsStateWithLifecycle().value
    val selectedCategory = viewModel.selectedCategory.collectAsStateWithLifecycle().value
    val recipes = viewModel.recipes.collectAsStateWithLifecycle().value
    val recipesAreLoading = viewModel.recipesAreLoading.collectAsStateWithLifecycle().value
    val queryRecipe = viewModel.queryRecipe.collectAsStateWithLifecycle().value

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
    ) {
        // SEARCH BAR (+ filters)
        item(
            span = { GridItemSpan(3) }
        ) {
            RecipeSearchBarSection(
                query = queryRecipe,
                onQueryChange = {
                    viewModel.updateQueryRecipe(it)
                },
                onSearch = {
                    viewModel.updateQueryRecipe(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        // RECIPE CATEGORIES
        item(
            span = { GridItemSpan(3) }
        ) {
            CategoriesRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    viewModel.updateSelectedCategory(category)
                }
            )
        }

        // RECIPES
        items(recipes) { recipe ->
            CachedAsyncImage(
                modifier = Modifier
                    .size(recipeItemSize)
                    .padding(16.dp)
                    .clickable { showRecipeDetails(recipe.id) },
                url = recipe.imgUrl,
                title = recipe.title
            )
        }

        // LOADING
        if (recipesAreLoading) {
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
        }
    }

}