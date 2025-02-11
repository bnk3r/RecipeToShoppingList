package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.converters.toUiModel

class GetRecipesByQueryUseCase(
    private val recipeRepository: RecipeRepository
) {

    suspend operator fun invoke(query: String): Flow<List<UiRecipe>> =
        withContext(Dispatchers.IO) {
            recipeRepository.getRecipesByQuery(query).map { recipes ->
                recipes.map { it.toUiModel() }
            }
        }

}