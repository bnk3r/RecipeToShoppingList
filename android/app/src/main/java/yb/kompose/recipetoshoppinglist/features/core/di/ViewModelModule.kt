package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos.RecipePanelViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos.RecipesPanelViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.AddIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.DeleteIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.ResetShoppingListsCurrentValueUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.vimos.ShoppingListsDashboardViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.vimos.AddIngredientViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.vimos.ShoppingListViewModel

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

fun provideRecipePanelViewModel(
    getRecipeDetailedUseCase: GetRecipeDetailedUseCase
) = RecipePanelViewModel(
    getRecipeDetailedUseCase
)

fun provideShoppingListViewModel(
    getShoppingListUseCase: GetShoppingListUseCase,
    addIngredientUseCase: AddIngredientUseCase,
    deleteIngredientUseCase: DeleteIngredientUseCase
) = ShoppingListViewModel(
    getShoppingListUseCase,
    addIngredientUseCase,
    deleteIngredientUseCase
)

fun provideAddIngredientViewModel(
    getIngredientsUseCase: GetIngredientsUseCase,
    fetchAndSaveIngredientsUseCase: FetchAndSaveIngredientsUseCase,
    addIngredientUseCase: AddIngredientUseCase
) = AddIngredientViewModel(
    getIngredientsUseCase,
    fetchAndSaveIngredientsUseCase,
    addIngredientUseCase
)

val viewModelModule = module {
    viewModel { provideShoppingListsDashboardViewModel(get(), get(), get()) }
    viewModel { provideRecipesPanelViewModel(get(), get(), get(), get()) }
    viewModel { provideRecipePanelViewModel(get()) }
    viewModel { provideShoppingListViewModel(get(), get(), get()) }
    viewModel { provideAddIngredientViewModel(get(), get(), get()) }
}