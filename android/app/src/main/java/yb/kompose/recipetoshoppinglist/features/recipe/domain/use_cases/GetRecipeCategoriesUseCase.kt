package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.CategoriesRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.converters.toUiModel

class GetRecipeCategoriesUseCase(
    private val categoriesRepository: CategoriesRepository
) {

    operator fun invoke(): Flow<List<UiCategory>> =
        categoriesRepository.getCategories()
            .map { categories ->
                categories.map { category ->
                    category.toUiModel()
                }
            }

}