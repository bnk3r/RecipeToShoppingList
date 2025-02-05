package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.CategoriesRepository

class FetchAndSaveCategoriesUseCase(
    private val categoriesRepository: CategoriesRepository
) {

    suspend operator fun invoke() : Int = withContext(Dispatchers.IO) {
        val categories = categoriesRepository.fetchCategories()
        if (categories.isEmpty()) return@withContext 0
        categoriesRepository.saveCategories(categories)
    }

}