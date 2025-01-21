package yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Category
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Recipe

@Dao
interface CategoryDAO {

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCategory(category: Category): Long

    @Query("SELECT * FROM recipes WHERE categoryName = :name")
    fun getRecipesForCategory(name: String): Flow<List<Recipe>>

}