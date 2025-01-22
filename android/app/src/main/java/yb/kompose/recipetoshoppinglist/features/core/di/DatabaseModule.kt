package yb.kompose.recipetoshoppinglist.features.core.di

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.core.data.db.RecipeToShoppingListDB

fun providesRecipeToShoppingDB(
    context: Context
) = Room
    .databaseBuilder(
        context = context,
        klass = RecipeToShoppingListDB::class.java,
        name = "recipe-to-shopping-list-db"
    )
    .fallbackToDestructiveMigration()
    .build()

fun providesCategoryDAO(db: RecipeToShoppingListDB) = db.categoryDAO()

fun provideRecipeDAO(db: RecipeToShoppingListDB) = db.recipeDAO()

val databaseModule = module {
    single { providesRecipeToShoppingDB(get()) }
    single { providesCategoryDAO(get()) }
    single { provideRecipeDAO(get()) }
}