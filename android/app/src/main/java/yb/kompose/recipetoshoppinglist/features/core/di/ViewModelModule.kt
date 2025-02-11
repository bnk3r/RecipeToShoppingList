package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.vimos.RecipesPanelViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.IngredientsViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.ResetShoppingListsCurrentValueUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.vimos.ShoppingListsDashboardViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.vimos.AddIngredientViewModel

fun provideRecipeViewModel(
    getRecipesForCategoryUseCase: GetRecipesForCategoryUseCase,
    getRecipesByQueryUseCase: GetRecipesByQueryUseCase,
    getRecipeDetailedUseCase: GetRecipeDetailedUseCase
) = RecipeViewModel(
    getRecipesForCategoryUseCase,
    getRecipesByQueryUseCase,
    getRecipeDetailedUseCase
)

fun provideIngredientsViewModel(
    fetchAndSaveIngredientsUseCase: FetchAndSaveIngredientsUseCase
) = IngredientsViewModel(
    fetchAndSaveIngredientsUseCase
)

fun provideShoppingListsDashboardViewModel(
    getShoppingListsUseCase: GetShoppingListsUseCase,
    addShoppingListUseCase: AddShoppingListUseCase,
    resetShoppingListsCurrentValueUseCase: ResetShoppingListsCurrentValueUseCase
) = ShoppingListsDashboardViewModel(
    getShoppingListsUseCase,
    addShoppingListUseCase,
    resetShoppingListsCurrentValueUseCase
)

fun provideRecipesPanelViewModel(
    getCategoriesUseCase: GetRecipeCategoriesUseCase,
    fetchAndSaveCategoriesUseCase: FetchAndSaveCategoriesUseCase,
    getRecipesForCategoryUseCase: GetRecipesForCategoryUseCase,
    getRecipesByQueryUseCase: GetRecipesByQueryUseCase
) = RecipesPanelViewModel(
    getCategoriesUseCase,
    fetchAndSaveCategoriesUseCase,
    getRecipesForCategoryUseCase,
    getRecipesByQueryUseCase
)

fun provideAddIngredientViewModel() = AddIngredientViewModel()

val viewModelModule = module {
    viewModel { provideRecipeViewModel(get(), get(), get()) }
    viewModel { provideIngredientsViewModel(get()) }
    viewModel { provideShoppingListsDashboardViewModel(get(), get(), get()) }
    viewModel { provideAddIngredientViewModel() }
    viewModel { provideRecipesPanelViewModel(get(), get(), get(), get()) }
}