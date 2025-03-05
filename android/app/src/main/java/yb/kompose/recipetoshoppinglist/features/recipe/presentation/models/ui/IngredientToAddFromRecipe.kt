package yb.kompose.recipetoshoppinglist.features.recipe.presentation.models.ui

import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit

data class IngredientToAddFromRecipe(
    val id: Long,
    val name: String,
    val amount: Int?,
    val originalAmountDescription: String,
    val unit: MeasureUnit,
    val imgUrl: String?
)
