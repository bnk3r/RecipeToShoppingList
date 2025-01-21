package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
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
                        instructions =,
                        ingredients =,
                        imgUrl =,
                        thumbnailUrl =,
                        recipeUrl =,
                        category =,
                        area =
                    )
                }
            }
        }
}