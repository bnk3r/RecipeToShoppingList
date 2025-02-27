package yb.kompose.recipetoshoppinglist.features.recipe.presentation.screen.models

import kotlinx.coroutines.Job
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe

data class RecipesScreenState(
    val categories: List<UiCategory> = emptyList(),
    val selectedCategory: UiCategory? = null,
    val recipes: List<UiRecipe>? = null,
    val areRecipesLoading: Boolean = false,
    val searchQuery: String = "",
    val getRecipesJob: Job? = null
)
