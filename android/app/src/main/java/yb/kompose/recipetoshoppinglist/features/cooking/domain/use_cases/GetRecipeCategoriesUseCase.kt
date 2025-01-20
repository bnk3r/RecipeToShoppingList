package yb.kompose.recipetoshoppinglist.features.cooking.domain.use_cases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.cooking.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.cooking.domain.models.UiCategory

class GetRecipeCategoriesUseCase(
    private val recipeRepository: RecipeRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): Flow<List<UiCategory>> = withContext(defaultDispatcher) {
        recipeRepository.getCategories().map { categories ->
            categories.map { category ->
                UiCategory(
                    name = category.name
                )
            }
        }
    }

}