package yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.ui

import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit

data class IngredientToAddFromShoppingList(
    val id: Long = 0,
    val shoppingListId: Long? = null,
    val selectedIngredient: UiIngredient? = null,
    val amount: Int? = null,
    val unit: MeasureUnit = MeasureUnit.NONE
)