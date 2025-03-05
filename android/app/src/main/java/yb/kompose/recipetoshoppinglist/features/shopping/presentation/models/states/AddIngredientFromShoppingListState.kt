package yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states

import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.ui.IngredientToAddFromShoppingList

data class AddIngredientFromShoppingListState(
    val ingredients: List<UiIngredient>? = null,
    val units: List<Int> = MeasureUnit.entries.map { it.stringRes },
    val ingredientToAdd: IngredientToAddFromShoppingList = IngredientToAddFromShoppingList(),
    val isIngredientToAddValid: Boolean = false
)