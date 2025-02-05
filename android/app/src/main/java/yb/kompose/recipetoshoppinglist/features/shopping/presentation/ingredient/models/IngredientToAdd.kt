package yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.models

import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient

data class IngredientToAdd(
    val id: Long = 0,
    val shoppingListId: Long? = null,
    val selectedIngredient: SelectionIngredient? = null,
    val amount: Int = 0,
    val unit: String = MeasureUnit.NONE.displayName
) {
    fun toShoppingIngredient() = UiShoppingListIngredient(
        id = id,
        shoppingListId = shoppingListId ?: throw IllegalArgumentException("No shopping list ID."),
        name = selectedIngredient?.name ?: throw IllegalArgumentException("No name."),
        amount = amount,
        unit = unit,
        imageUrl = selectedIngredient.imageUrl
    )
}