package yb.kompose.recipetoshoppinglist.features.recipe.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UiIngredient(
    val name: String,
    val amount: String,
    val imgUrl: String?,
    val thumbnailUrl: String?
)