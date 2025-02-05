package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models

import kotlinx.serialization.Serializable

@Serializable
object RecipesDestination

@Serializable
data class RecipeDestination(
    val id: Int
)

@Serializable
object ShoppingDestination