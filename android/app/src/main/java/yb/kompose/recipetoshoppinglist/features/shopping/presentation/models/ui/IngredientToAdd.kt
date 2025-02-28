package yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.ui

import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit

data class IngredientToAdd(
    val id: Long = 0,
    val shoppingListId: Long? = null,
    val selectedIngredient: SelectionIngredient? = null,
    val amount: Int = 0,
    val unit: String = MeasureUnit.NONE.displayName
)