package yb.kompose.recipetoshoppinglist.features.recipe.presentation.models

import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient

data class AddIngredientPanelState(
    val refIngredients: List<UiIngredient>? = null,
    val areRefIngredientsLoading: Boolean = false,
    val refIngredient: UiIngredient? = null,
    val ingredientToAdd: UiIngredient? = null
)
