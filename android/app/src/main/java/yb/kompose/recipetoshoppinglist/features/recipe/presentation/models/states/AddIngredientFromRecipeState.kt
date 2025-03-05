package yb.kompose.recipetoshoppinglist.features.recipe.presentation.models.states

import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.models.ui.IngredientToAddFromRecipe
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit

data class AddIngredientFromRecipeState(
    val currentShoppingListId: Long? = null,
    val ingredient: IngredientToAddFromRecipe? = null,
    val refIngredient: UiIngredient? = null,
    val refIngredients: List<UiIngredient>? = null,
    val measureUnits: List<Int> = MeasureUnit.entries.map { it.stringRes },
    val isSubmitValid: Boolean = false
)
