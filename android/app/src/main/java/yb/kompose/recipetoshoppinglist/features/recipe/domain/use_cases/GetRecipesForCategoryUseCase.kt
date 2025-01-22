package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Recipe
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe

class GetRecipesForCategoryUseCase(
    private val recipeRepository: RecipeRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(categoryName: String): Flow<List<UiRecipe>> =
        withContext(defaultDispatcher) {
            recipeRepository.getRecipesForCategory(categoryName).map { recipes ->
                recipes.map { recipe ->
                    UiRecipe(
                        id = recipe.id.toInt(),
                        title = recipe.name,
                        instructions = recipe.instructions,
                        ingredients = recipe.extractIngredients(),
                        imgUrl = recipe.imageUrl,
                        thumbnailUrl = recipe.thumbnailUrl,
                        recipeUrl = recipe.articleUrl,
                        category = recipe.categoryName,
                        area = recipe.areaName
                    )
                }
            }
        }
}

fun Recipe.extractIngredients(): List<UiIngredient> {
    return ingredients
        ?.split(",")
        ?.filter { it.isNotEmpty() }
        ?.filter { it.split(":").size == 2 }
        ?.map { part ->
            val splits = part.split(":")
            UiIngredient(
                name = splits[0],
                amount = splits[1],
                imgUrl = null,
                thumbnailUrl = null
            )
        }
        ?: emptyList()
}