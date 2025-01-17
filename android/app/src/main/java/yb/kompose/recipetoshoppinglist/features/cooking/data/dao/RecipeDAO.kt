package yb.kompose.recipetoshoppinglist.features.cooking.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import yb.kompose.recipetoshoppinglist.features.cooking.data.models.Recipe

@Dao
interface RecipeDAO {

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id IS :id")
    fun getRecipeById(id: Long): Flow<Recipe?>

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :name || '%'")
    fun getRecipesByName(name: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE categoryId IS :categoryId")
    fun getRecipesByCategory(categoryId: Long): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE areaId IS :areaId")
    fun getRecipesByArea(areaId: Long): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE ingredients <> ''")
    fun getRecipesWithIngredients() : Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE imageUrl <> ''")
    fun getRecipesWithImageURL() : Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE youtubeVideoUrl <> ''")
    fun getRecipesWithYoutubeVideoURL() : Flow<List<Recipe>>

    @Insert
    fun addRecipe(recipe: Recipe) : Long

    @Update
    fun updateRecipe(recipe: Recipe)
}