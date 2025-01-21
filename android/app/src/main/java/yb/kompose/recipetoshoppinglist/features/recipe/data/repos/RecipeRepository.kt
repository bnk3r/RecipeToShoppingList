package yb.kompose.recipetoshoppinglist.features.recipe.data.repos

import android.util.Log
import kotlinx.coroutines.flow.Flow
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.service.TheMealDBService
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.CategoryDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Category

class RecipeRepository(
    private val remoteDataSource: TheMealDBService,
    private val localDataSource: CategoryDAO
) {

    suspend fun getCategories(): Flow<List<Category>> {
        fetchAndSaveCategories()
        return localDataSource.getAllCategories()
    }

    private suspend fun fetchAndSaveCategories() {
        val response = remoteDataSource.getMealCategoriesDetailed()
        if (response.isSuccessful) {
            response.body()?.categories?.let { responseCategories ->
                responseCategories
                    .map { category ->
                        Category(
                            id = category.idCategory.toLong(),
                            name = category.strCategory
                                ?: throw IllegalArgumentException("Category name cannot be null"),
                            imageUrl = category.strCategoryThumb,
                            description = category.strCategoryDescription,
                            displayOrderNumber = category.idCategory.toInt()
                        )
                    }
                    .forEach { category ->
                        localDataSource.addCategory(category)
                    }
            }
        } else {
            Log.e(
                "RecipeRepository",
                "Error fetching categories: ${response.message()}"
            )
        }
    }

}