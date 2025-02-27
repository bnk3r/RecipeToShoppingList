package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models

import kotlinx.serialization.Serializable

@Serializable
object ShoppingListsDestination

@Serializable
object RecipesDestination

@Serializable
object ProfileDestination

@Serializable
data class RecipeDestination(
    val id: Long
) {
    companion object {
        const val ID_ARG = "recipe_id_arg"
    }
}