package yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Recipe

@Dao
interface RecipeDAO {

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id IS :id")
    fun getRecipeById(id: Long): Flow<Recipe?>

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :name || '%'")
    fun getRecipesByName(name: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE categoryName IS :categoryName")
    fun getRecipesByCategory(categoryName: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE areaName IS :areaName")
    fun getRecipesByArea(areaName: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE ingredients <> ''")
    fun getRecipesWithIngredients() : Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE imageUrl <> ''")
    fun getRecipesWithImageURL() : Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE youtubeVideoUrl <> ''")
    fun getRecipesWithYoutubeVideoURL() : Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addRecipe(recipe: Recipe) : Long

    @Update
    fun updateRecipe(recipe: Recipe)

    @Query("SELECT * FROM ingredients")
    fun getIngredients(): Flow<List<Ingredient>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addIngredient(ingredient: Ingredient)

}