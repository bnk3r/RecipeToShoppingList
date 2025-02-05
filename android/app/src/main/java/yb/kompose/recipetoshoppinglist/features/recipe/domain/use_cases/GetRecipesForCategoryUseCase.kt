package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Recipe
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe

class GetRecipesForCategoryUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(categoryName: String): Flow<List<UiRecipe>> =
        withContext(Dispatchers.IO) {
            recipeRepository.getRecipesForCategory(categoryName).map { recipes ->
                recipes.map { it.toUiModel() }
            }
        }

    private suspend fun Recipe.toUiModel() =
        withContext(Dispatchers.Default) {
            UiRecipe(
                id = id.toInt(),
                title = name,
                instructions = instructions,
                ingredients = extractIngredients(),
                imgUrl = imageUrl,
                thumbnailUrl = thumbnailUrl,
                recipeUrl = articleUrl,
                category = categoryName,
                area = areaName
            )
        }

    private suspend fun Recipe.extractIngredients() =
        withContext(Dispatchers.Default) {
            ingredients
                ?.split(",")
                ?.filter { it.isNotEmpty() }
                ?.filter { it.split(":").size == 2 }
                ?.map { part ->
                    val splits = part.split(":")
                    UiIngredient(
                        name = splits[0],
                        amount = splits[1],
                        imgUrl = "https://www.themealdb.com/images/ingredients/${splits[0]}.png",
                        thumbnailUrl = "https://www.themealdb.com/images/ingredients/${splits[0]}-Small.png"
                    )
                }
                ?: emptyList()
        }
}