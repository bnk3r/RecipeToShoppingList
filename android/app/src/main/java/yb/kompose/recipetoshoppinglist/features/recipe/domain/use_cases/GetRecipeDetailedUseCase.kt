package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.converters.toUiModel

class GetRecipeDetailedUseCase(
    private val recipeRepository: RecipeRepository
) {

    suspend operator fun invoke(id: Long): Flow<UiRecipe?> = withContext(Dispatchers.IO) {
        recipeRepository.getRecipeDetailed(id).map { it?.toUiModel() }
    }

}