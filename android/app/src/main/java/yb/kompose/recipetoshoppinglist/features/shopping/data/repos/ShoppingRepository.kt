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
                addShoppingListIngredient(ingredient)
            }
            if (shoppingList.shoppingList.current) {
                removeCurrentStatusFromOtherShoppingLists(id.toLong())
            }
            id.toLong()
        }

    suspend fun updateShoppingList(shoppingList: ShoppingListWithIngredients) =
        withContext(Dispatchers.IO) {
            shoppingDao.updateShoppingList(shoppingList.shoppingList)
            shoppingList.ingredients.forEach { ingredient ->
                updateShoppingListIngredient(ingredient)
            }
        }

    suspend fun deleteShoppingList(shoppingList: ShoppingListWithIngredients) =
        withContext(Dispatchers.IO) {
            shoppingDao.deleteShoppingList(shoppingList.shoppingList)
            shoppingList.ingredients.forEach { ingredient ->
                deleteShoppingListIngredient(ingredient)
            }
        }

    suspend fun getShoppingListIngredient(id: Long) =
        withContext(Dispatchers.IO) {
            shoppingDao.getShoppingListIngredientById(id)
        }

    suspend fun addShoppingListIngredient(ingredient: ShoppingListIngredient) =
        withContext(Dispatchers.IO) {
            shoppingDao.addShoppingListIngredient(ingredient)
        }

    suspend fun updateShoppingListIngredient(ingredient: ShoppingListIngredient) =
        withContext(Dispatchers.IO) {
            shoppingDao.updateShoppingListIngredient(ingredient)
        }

    suspend fun deleteShoppingListIngredient(ingredient: ShoppingListIngredient) =
        withContext(Dispatchers.IO) {
            shoppingDao.deleteShoppingListIngredient(ingredient)
        }

    suspend fun getCurrentShoppingList() =
        withContext(Dispatchers.IO) {
            shoppingDao.getCurrentShoppingList()
        }

    private suspend fun removeCurrentStatusFromOtherShoppingLists(currentId: Long) =
        withContext(Dispatchers.IO) {
            getShoppingLists().collect { lists ->
                lists
                    .filter { it.shoppingList.id != currentId }
                    .forEach { list ->
                        updateShoppingList(
                            list.copy(
                                shoppingList = list.shoppingList.copy(
                                    current = false
                                )
                            )
                        )
                    }
            }
        }
}