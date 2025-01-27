package yb.kompose.recipetoshoppinglist.features.shopping.domain.models

import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit

data class UiShoppingListIngredient(
    val id: Long,
    val shoppingListId: Long,
    val name: String,
    val amount: Double,
    val unit: MeasureUnit,
    val imageUrl: String?
)