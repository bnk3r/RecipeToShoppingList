package yb.kompose.recipetoshoppinglist.features.shopping.data.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.dao.ShoppingDao
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListWithIngredients
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.util.toShoppingIngredient

class ShoppingRepository(
    private val shoppingDao: ShoppingDao,
    private val recipeRepository: RecipeRepository
) {

    suspend fun getShoppingLists() = withContext(Dispatchers.IO) {
        shoppingDao.getShoppingLists()
    }

    suspend fun getShoppingList(id: Long) = withContext(Dispatchers.IO) {
        shoppingDao.getShoppingListById(id)
    }

    suspend fun addShoppingList(shoppingList: ShoppingListWithIngredients) =
        withContext(Dispatchers.IO) {
            val id = shoppingDao.addShoppingList(shoppingList.shoppingList).toInt()
            if (id == -1) return@withContext id.toLong()
            shoppingList.ingredients.forEach { ingredient ->
                shoppingDao.addShoppingListIngredient(ingredient)
            }
            id.toLong()
        }

    suspend fun updateShoppingList(shoppingList: ShoppingListWithIngredients) =
        withContext(Dispatchers.IO) {
            shoppingDao.updateShoppingList(shoppingList.shoppingList)
            shoppingList.ingredients.forEach { ingredient ->
                shoppingDao.updateShoppingListIngredient(ingredient)
            }
        }

    suspend fun deleteShoppingList(shoppingList: ShoppingListWithIngredients) =
        withContext(Dispatchers.IO) {
            shoppingDao.deleteShoppingList(shoppingList.shoppingList)
            shoppingList.ingredients.forEach { ingredient ->
                shoppingDao.deleteShoppingListIngredient(ingredient)
            }
        }

    suspend fun deleteShoppingListIngredient(ingredient: ShoppingListIngredient) =
        withContext(Dispatchers.IO) {
            shoppingDao.deleteShoppingListIngredient(ingredient)
        }

    suspend fun getIngredients(): Flow<List<ShoppingListIngredient>> =
        withContext(Dispatchers.IO) {
            recipeRepository.getIngredients().map { ingredients ->
                ingredients.map { ingredient ->
                    ingredient.toShoppingIngredient()
                }
            }
        }

}