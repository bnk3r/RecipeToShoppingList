package yb.kompose.recipetoshoppinglist.features.core.domain.models.nav

import kotlinx.serialization.Serializable

@Serializable
object RecipesDestination

@Serializable
data class RecipeDestination(
    val id: Int
)