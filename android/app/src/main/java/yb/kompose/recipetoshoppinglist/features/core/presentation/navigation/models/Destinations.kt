package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models

import kotlinx.serialization.Serializable
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient

@Serializable
object ShoppingListsDestination

@Serializable
object RecipesDestination

@Serializable
object ProfileDestination

@Serializable
data class ShoppingListDestination(
    val id: Long
)

@Serializable
data class RecipeDestination(
    val id: Long
)

@Serializable
data class AddIngredientFromShoppingListDestination(
    val id: Long
)

@Serializable
data class AddIngredientFromRecipeDestination(
    val ingredient: UiIngredient
)