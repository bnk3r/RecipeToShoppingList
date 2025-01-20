package yb.kompose.recipetoshoppinglist.features.cooking.data.repos

import kotlinx.coroutines.flow.Flow
import yb.kompose.recipetoshoppinglist.features.cooking.data.api.service.TheMealDBService
import yb.kompose.recipetoshoppinglist.features.cooking.data.db.dao.CategoryDAO
import yb.kompose.recipetoshoppinglist.features.cooking.data.db.models.Category

class RecipeRepository(
    private val remoteDataSource: TheMealDBService,
    private val localDataSource: CategoryDAO
) {

    fun getCategories() : Flow<List<Category>> {
        // TODO :
        // from local
        // empty ?
        // yes : from remote -> save to local
        // no : from local
        return localDataSource.getAllCategories()
    }

}