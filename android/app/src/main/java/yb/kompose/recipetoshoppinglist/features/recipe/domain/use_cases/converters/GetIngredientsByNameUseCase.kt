package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.converters

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetIngredientsUseCase

class GetIngredientsByNameUseCase(
    private val getIngredientsUseCase: GetIngredientsUseCase
) {

    operator fun invoke(name: String): Flow<List<UiIngredient>> =
        getIngredientsUseCase()
            .map { ingredients ->
                ingredients.filter { ingredient ->
                    ingredient.name.contains(name, ignoreCase = true)
                }
            }
            .flowOn(Dispatchers.Default)

}