package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase

fun provideGetRecipeCategoriesUseCase(recipeRepository: RecipeRepository) =
    GetRecipeCategoriesUseCase(recipeRepository)

fun provideGetRecipesForCategoryUseCase(recipeRepository: RecipeRepository) =
    GetRecipesForCategoryUseCase(recipeRepository)

val useCaseModule = module {
    factory { provideGetRecipeCategoriesUseCase(get()) }
    factory { provideGetRecipesForCategoryUseCase(get()) }
}