package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel

fun provideCategoryViewModel(getRecipeCategoriesUseCase: GetRecipeCategoriesUseCase) =
    CategoryViewModel(getRecipeCategoriesUseCase)

fun provideRecipeViewModel(
    getRecipesForCategoryUseCase: GetRecipesForCategoryUseCase,
    getRecipesByQueryUseCase: GetRecipesByQueryUseCase,
    getRecipeDetailedUseCase: GetRecipeDetailedUseCase
) = RecipeViewModel(
    getRecipesForCategoryUseCase,
    getRecipesByQueryUseCase,
    getRecipeDetailedUseCase
)

val viewModelModule = module {
    viewModel { provideCategoryViewModel(get()) }
    viewModel { provideRecipeViewModel(get(), get(), get()) }
}