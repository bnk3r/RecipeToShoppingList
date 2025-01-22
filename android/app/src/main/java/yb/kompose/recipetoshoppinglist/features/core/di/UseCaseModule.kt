package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase

fun provideGetRecipeCategoriesUseCase(recipeRepository: RecipeRepository) =
    GetRecipeCategoriesUseCase(recipeRepository)

fun provideGetRecipesForCategoryUseCase(recipeRepository: RecipeRepository) =
    GetRecipesForCategoryUseCase(recipeRepository)

fun provideGetRecipesByQueryUseCase(recipeRepository: RecipeRepository) =
    GetRecipesByQueryUseCase(recipeRepository)

fun provideGetRecipeDetailedUseCase(recipeRepository: RecipeRepository) =
    GetRecipeDetailedUseCase(recipeRepository)

val useCaseModule = module {
    factory { provideGetRecipeCategoriesUseCase(get()) }
    factory { provideGetRecipesForCategoryUseCase(get()) }
    factory { provideGetRecipesByQueryUseCase(get()) }
    factory { provideGetRecipeDetailedUseCase(get()) }
}