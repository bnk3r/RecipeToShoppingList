package yb.kompose.recipetoshoppinglist.features.recipe.presentation.screen.models

import kotlinx.coroutines.Job
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe

data class RecipeScreenState(
    val recipeId: Long? = null,
    val recipe: UiRecipe? = null,
    val isRecipeLoading: Boolean = false,
    val getRecipeJob: Job? = null,
    val ingredientToAdd: UiIngredient? = null,
    val isAddIngredientPanelVisible: Boolean = false
)
