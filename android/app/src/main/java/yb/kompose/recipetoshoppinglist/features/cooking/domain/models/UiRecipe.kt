package yb.kompose.recipetoshoppinglist.features.cooking.domain.models

data class UiRecipe(
    val id: Int,
    val title: String,
    val instructions: String,
    val ingredients: List<UiIngredient> = listOf(),
    val imgUrl: String,
    val thumbnailUrl: String,
    val recipeUrl: String,
    val category: String,
    val area: String
)
