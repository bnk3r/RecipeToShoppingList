package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.service.TheMealDBService
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.CategoryDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.RecipeDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.dao.ShoppingDao
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository

fun provideRecipeRepository(
    remoteDataSource: TheMealDBService,
    categoryDAO: CategoryDAO,
    recipeDAO: RecipeDAO
) = RecipeRepository(remoteDataSource, categoryDAO, recipeDAO)

fun provideShoppingRepository(
    shoppingDao: ShoppingDao
) = ShoppingRepository(shoppingDao)

val repositoryModule = module {
    single { provideRecipeRepository(get(), get(), get()) }
    single { provideShoppingRepository(get()) }
}