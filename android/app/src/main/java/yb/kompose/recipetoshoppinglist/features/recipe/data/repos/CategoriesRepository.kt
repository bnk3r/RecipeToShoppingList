package yb.kompose.recipetoshoppinglist.features.recipe.data.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.category.CategoryDetailed
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.service.TheMealDBService
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.CategoryDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Category

class CategoriesRepository(
    private val apiService: TheMealDBService,
    private val categoriesDao: CategoryDAO
) {

    suspend fun fetchCategories(): List<CategoryDetailed> = withContext(Dispatchers.IO) {
        apiService.getCategoriesDetailed().body()?.categories ?: emptyList()
    }

    fun getCategories(): Flow<List<Category>> =
        categoriesDao.getAllCategories().flowOn(Dispatchers.IO)

    suspend fun saveCategories(categories: List<CategoryDetailed>): Int =
        withContext(Dispatchers.IO) {
            val ids = mutableListOf<Long>()
            categories.forEach {
                val id = categoriesDao.addCategory(it.toDBEntity())
                if (id != -1L) {
                    ids.add(id)
                }
            }
            ids.size
        }

    private fun CategoryDetailed.toDBEntity() =
        Category(
            id = idCategory.toLong(),
            name = strCategory ?: throw IllegalArgumentException("Category name cannot be null"),
            imageUrl = strCategoryThumb,
            description = strCategoryDescription,
            displayOrderNumber = idCategory.toInt()
        )

}