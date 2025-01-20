package yb.kompose.recipetoshoppinglist.features.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import yb.kompose.recipetoshoppinglist.features.cooking.data.db.dao.RecipeDAO
import yb.kompose.recipetoshoppinglist.features.cooking.data.db.models.Area
import yb.kompose.recipetoshoppinglist.features.cooking.data.db.models.Category
import yb.kompose.recipetoshoppinglist.features.cooking.data.db.models.Ingredient
import yb.kompose.recipetoshoppinglist.features.cooking.data.db.models.Recipe

@Database(
    entities = [
        Recipe::class,
        Ingredient::class,
        Category::class,
        Area::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RecipeToShoppingListDB : RoomDatabase() {
    abstract fun recipeDAO(): RecipeDAO
}