package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.data.api.service.TheMealDBService
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.CategoryDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.IngredientsDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.dao.RecipeDAO
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.CategoriesRepository
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.IngredientsRepository
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.dao.ShoppingDao
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository

fun provideRecipeRepository(
    remoteDataSource: TheMealDBService,
    recipeDAO: RecipeDAO
) = RecipeRepository(remoteDataSource, recipeDAO)

fun provideIngredientsRepository(
    mealDBService: TheMealDBService,
    ingredientsDAO: IngredientsDAO
) = IngredientsRepository(mealDBService, ingredientsDAO)

fun provideCategoriesRepository(
    mealDBService: TheMealDBService,
    categoryDAO: CategoryDAO
) = CategoriesRepository(mealDBService, categoryDAO)

fun provideShoppingRepository(
    shoppingDao: ShoppingDao
) = ShoppingRepository(shoppingDao)

val repositoryModule = module {
    single { provideRecipeRepository(get(), get()) }
    single { provideIngredientsRepository(get(), get()) }
    single { provideCategoriesRepository(get(), get()) }
    single { provideShoppingRepository(get()) }
}