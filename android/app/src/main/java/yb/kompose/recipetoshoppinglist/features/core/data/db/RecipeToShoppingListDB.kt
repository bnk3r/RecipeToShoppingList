package yb.kompose.recipetoshoppinglist.features.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import yb.kompose.recipetoshoppinglist.features.cooking.data.dao.RecipeDAO
import yb.kompose.recipetoshoppinglist.features.cooking.data.models.Area
import yb.kompose.recipetoshoppinglist.features.cooking.data.models.Category
import yb.kompose.recipetoshoppinglist.features.cooking.data.models.Ingredient
import yb.kompose.recipetoshoppinglist.features.cooking.data.models.Recipe

@Database(
    entities = [
        Recipe::class,
        Ingredient::class,
        Category::class,
        Area::class
    ],
    version = 1
)
abstract class RecipeToShoppingListDB : RoomDatabase() {
    abstract fun recipeDAO(): RecipeDAO
}