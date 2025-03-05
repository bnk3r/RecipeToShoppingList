package yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states

import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.ui.IngredientToAddFromShoppingList

data class AddIngredientFromShoppingListState(
    val ingredients: List<UiIngredient>? = null,
    val units: List<String>? = null,
    val ingredientToAdd: IngredientToAddFromShoppingList = IngredientToAddFromShoppingList(),
    val isIngredientToAddValid: Boolean = false
)