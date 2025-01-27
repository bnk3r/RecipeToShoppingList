package yb.kompose.recipetoshoppinglist.features.shopping.data.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.dao.ShoppingDao
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListWithIngredients

class ShoppingRepository(
    private val shoppingDao: ShoppingDao
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

}