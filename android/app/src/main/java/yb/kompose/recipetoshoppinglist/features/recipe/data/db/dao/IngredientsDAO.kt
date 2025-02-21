package yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient

@Dao
interface IngredientsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addIngredient(ingredient: Ingredient) : Long

}