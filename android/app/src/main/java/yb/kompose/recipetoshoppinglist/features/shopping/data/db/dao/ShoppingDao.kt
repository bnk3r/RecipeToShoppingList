package yb.kompose.recipetoshoppinglist.features.shopping.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListWithIngredients

@Dao
interface ShoppingDao {

    @Transaction
    @Query("SELECT * FROM shopping_lists")
    fun getShoppingLists(): Flow<List<ShoppingListWithIngredients>>

    @Transaction
    @Query("SELECT * FROM shopping_lists WHERE id IS :id")
    fun getShoppingListById(id: Long): Flow<ShoppingListWithIngredients?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addShoppingList(shoppingList: ShoppingList): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addShoppingListIngredient(ingredient: ShoppingListIngredient): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateShoppingList(shoppingList: ShoppingList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateShoppingListIngredient(ingredient: ShoppingListIngredient)

    @Delete
    fun deleteShoppingList(shoppingList: ShoppingList)

    @Delete
    fun deleteShoppingListIngredient(ingredient: ShoppingListIngredient)

    @Query("SELECT * FROM ingredients")
    fun getIngredients() : Flow<List<Ingredient>>

}

