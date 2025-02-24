package yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.models

data class AddShoppingIngredientPanelState(
    val shoppingListId: Long? = null,
    val ingredients: List<SelectionIngredient>? = null,
    val areIngredientsLoading: Boolean = false,
    val units: List<String>? = null,
    val ingredientToAdd: IngredientToAdd = IngredientToAdd(),
    val isIngredientToAddValid: Boolean = false
)