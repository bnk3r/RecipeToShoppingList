package yb.kompose.recipetoshoppinglist.features.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.CategoryDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.RecipeDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Area
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Category
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Recipe
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.dao.ShoppingDao
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.util.LocalDateTypeConverters
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.util.MeasureUnitTypeConverters
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient

@Database(
    entities = [
        Recipe::class,
        Ingredient::class,
        Category::class,
        Area::class,
        ShoppingList::class,
        ShoppingListIngredient::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    value = [
        MeasureUnitTypeConverters::class,
        LocalDateTypeConverters::class
    ]
)
abstract class RecipeToShoppingListDB : RoomDatabase() {
    abstract fun recipeDAO(): RecipeDAO
    abstract fun categoryDAO(): CategoryDAO
    abstract fun shoppingDAO(): ShoppingDao
}