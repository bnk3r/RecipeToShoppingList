package yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.components.RowCategoriesSection
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.recipes.components.RecipesGrid
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            item {
                RecipeSearchBarSection(
                    query = queryRecipe,
                    onQueryChange = { queryRecipe = it },
                    onSearch = {/* TODO */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            item {
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
            item {
                RecipesGrid(
                    recipes = recipesForCategory,
                    onRecipeClick = {}
                )
            }
        }
    }

}