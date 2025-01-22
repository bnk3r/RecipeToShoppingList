package yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.components.RowCategoriesSection
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.recipes.vimos.RecipeViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.search.components.RecipeSearchBarSection

@Composable
fun RecipesScreen(
    categoryViewModel: CategoryViewModel,
    recipeViewModel: RecipeViewModel,
    modifier: Modifier = Modifier
) {
    var queryRecipe by remember { mutableStateOf("") }

    val categories = categoryViewModel.categories.collectAsStateWithLifecycle().value
    var selectedCategory by remember(categories) { mutableStateOf(categories.firstOrNull()) }

    var recipesForCategory by remember { mutableStateOf(listOf<UiRecipe>()) }

    val configuration = LocalConfiguration.current
    val recipeItemSize = configuration.screenWidthDp.dp / 3

    LaunchedEffect(selectedCategory) {
        selectedCategory?.let { category ->
            recipeViewModel.getRecipesForCategory(category.name).collect { recipes ->
                recipesForCategory = recipes
            }
        }
    }

    Scaffold(
        modifier = modifier
    ) { innerPadding ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            item(
                span = { GridItemSpan(3) }
            ) {
                RecipeSearchBarSection(
                    query = queryRecipe,
                    onQueryChange = { queryRecipe = it },
                    onSearch = {/* TODO */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            item(
                span = { GridItemSpan(3) }
            ) {
                selectedCategory?.let { selected ->
                    RowCategoriesSection(
                        categories = categories,
                        selectedCategory = selected,
                        onCategorySelected = { selectedCategory = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp)
                    )
                }
            }

            items(recipesForCategory) { recipe ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(recipe.imgUrl)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = recipe.title,
                    placeholder = painterResource(R.drawable.loading),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(recipeItemSize)
                )
            }

        }

    }

}