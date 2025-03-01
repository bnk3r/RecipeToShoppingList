package yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states

import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.ui.IngredientToAdd
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.ui.SelectionIngredient

data class AddIngredientFromShoppingListState(
    val shoppingListId: Long? = null,
    val ingredients: List<SelectionIngredient>? = null,
    val areIngredientsLoading: Boolean = false,
    val units: List<String>? = null,
    val ingredientToAdd: IngredientToAdd = IngredientToAdd(),
    val isIngredientToAddValid: Boolean = false
)