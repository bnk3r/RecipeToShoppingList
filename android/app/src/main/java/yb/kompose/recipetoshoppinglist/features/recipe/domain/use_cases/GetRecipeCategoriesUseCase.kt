package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.CategoriesRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.converters.toUiModel

class GetRecipeCategoriesUseCase(
    private val categoriesRepository: CategoriesRepository
) {

    suspend operator fun invoke(): Flow<List<UiCategory>> = withContext(Dispatchers.IO) {
        categoriesRepository.getCategories().map { categories ->
            categories.map { it.toUiModel() }
        }
    }

}