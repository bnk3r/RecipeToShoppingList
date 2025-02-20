package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.converters.toUiModel

class GetIngredientsUseCase(
    private val recipeRepository: RecipeRepository
) {

    operator fun invoke(): Flow<List<UiIngredient>> =
        recipeRepository.getIngredients()
            .map { ingredients ->
                ingredients.map { ingredient ->
                    ingredient.toUiModel()
                }
            }
            .flowOn(Dispatchers.Default)

}