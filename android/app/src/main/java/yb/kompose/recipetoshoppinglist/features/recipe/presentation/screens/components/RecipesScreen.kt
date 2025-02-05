package yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.components.RowCategoriesSection
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.search.components.RecipeSearchBarSection
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel

@Composable
fun RecipesScreen(
    categoryViewModel: CategoryViewModel,
    recipeViewModel: RecipeViewModel,
    showRecipeDetails: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    var queryRecipe by remember { mutableStateOf("") }

    val categories = categoryViewModel.categories.collectAsStateWithLifecycle().value
    var selectedCategory by remember(categories) { mutableStateOf(categories.firstOrNull()) }

    var recipesForCategory by remember { mutableStateOf(listOf<UiRecipe>()) }
    var recipesForCategoryLoading by remember { mutableStateOf(false) }
    var recipesForCategoryJob by remember { mutableStateOf<Job?>(null) }

    val configuration = LocalConfiguration.current
    val recipeItemSize = configuration.screenWidthDp.dp / 3

    var recipesByQuery by remember { mutableStateOf(listOf<UiRecipe>()) }
    var recipesByQueryLoading by remember { mutableStateOf(false) }
    var recipesByQueryJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(selectedCategory) {
        selectedCategory?.let { category ->
            recipesForCategoryJob?.cancel()
            recipesForCategory = emptyList()
            recipesByQuery = emptyList()
            recipesForCategoryLoading = true
            recipesForCategoryJob = coroutineScope.launch(Dispatchers.Default) {
                recipeViewModel.getRecipesForCategory(category.name).collect { recipes ->
                    withContext(Dispatchers.Main) {
                        recipesForCategory = recipes
                        recipesForCategoryLoading = false
                    }
                }
            }
        }
    }

    fun searchByQuery() {
        if (queryRecipe.isBlank()) return
        recipesByQueryJob?.cancel()
        selectedCategory = null
        recipesForCategory = emptyList()
        recipesByQuery = emptyList()
        recipesByQueryLoading = true
        recipesByQueryJob = coroutineScope.launch(Dispatchers.Default) {
            recipeViewModel.getRecipesByQuery(queryRecipe).collect { recipes ->
                withContext(Dispatchers.Main) {
                    recipesByQuery = recipes
                    recipesByQueryLoading = false
                }
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
    ) {
        item(
            span = { GridItemSpan(3) }
        ) {
            RecipeSearchBarSection(
                query = queryRecipe,
                onQueryChange = { queryRecipe = it },
                onSearch = {
                    searchByQuery()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item(
            span = { GridItemSpan(3) }
        ) {
            RowCategoriesSection(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = {
                    selectedCategory = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            )
        }

        if (recipesForCategory.isNotEmpty() && recipesByQuery.isEmpty()) {
            items(recipesForCategory) { recipe ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(recipe.imgUrl)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = recipe.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(recipeItemSize)
                        .padding(16.dp)
                        .clickable {
                            showRecipeDetails(recipe.id)
                        }
                )
            }
        }

        if (recipesByQuery.isNotEmpty()) {
            items(recipesByQuery) { recipe ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(recipe.imgUrl)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = recipe.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(recipeItemSize)
                        .padding(16.dp)
                        .clickable {
                            showRecipeDetails(recipe.id)
                        }
                )
            }
        }

        if (recipesForCategoryLoading || recipesByQueryLoading) {
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