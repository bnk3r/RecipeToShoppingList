package yb.kompose.recipetoshoppinglist.features.cooking.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import yb.kompose.recipetoshoppinglist.features.cooking.data.db.models.Category

@Dao
interface CategoryDAO {

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

}