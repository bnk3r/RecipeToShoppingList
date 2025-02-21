package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.IngredientsRepository

class FetchAndSaveIngredientsUseCase(
    private val ingredientsRepository: IngredientsRepository
) {

    suspend operator fun invoke(): Int = withContext(Dispatchers.IO) {
        ingredientsRepository.saveIngredients(ingredientsRepository.fetchIngredients())
    }

}