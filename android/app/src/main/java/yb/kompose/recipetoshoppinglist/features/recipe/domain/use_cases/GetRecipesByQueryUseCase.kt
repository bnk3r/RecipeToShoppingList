package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe

class GetRecipesByQueryUseCase(
    private val recipeRepository: RecipeRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(query: String): Flow<List<UiRecipe>> = withContext(defaultDispatcher) {
        recipeRepository.getRecipesByQuery(query).map { recipes ->
            recipes.map { recipe ->
                UiRecipe(
                    id = recipe.id.toInt(),
                    title = recipe.name,
                    instructions = recipe.instructions,
                    ingredients = recipe.ingredients
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
                        ?: emptyList(),
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