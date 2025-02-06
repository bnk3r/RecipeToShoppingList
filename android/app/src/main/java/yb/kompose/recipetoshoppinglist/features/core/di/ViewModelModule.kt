package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.DeleteIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.DeleteShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.IngredientsViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.AddIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetCurrentShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.UpdateIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.UpdateShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.AddIngredientViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingViewModel

fun provideCategoryViewModel(
    getRecipeCategoriesUseCase: GetRecipeCategoriesUseCase,
    fetchAndSaveCategoriesUseCase: FetchAndSaveCategoriesUseCase
) = CategoryViewModel(getRecipeCategoriesUseCase, fetchAndSaveCategoriesUseCase)

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
) = IngredientsViewModel(fetchAndSaveIngredientsUseCase)

fun provideShoppingViewModel(
    getShoppingListsUseCase: GetShoppingListsUseCase,
    getShoppingListUseCase: GetShoppingListUseCase,
    addShoppingListUseCase: AddShoppingListUseCase,
    updateShoppingListUseCase: UpdateShoppingListUseCase,
    deleteShoppingListUseCase: DeleteShoppingListUseCase,
    deleteIngredientUseCase: DeleteIngredientUseCase,
    getIngredientsUseCase: GetIngredientsUseCase,
    getCurrentShoppingListUseCase: GetCurrentShoppingListUseCase,
    addIngredientUseCase: AddIngredientUseCase,
    updateIngredientUseCase: UpdateIngredientUseCase
) = ShoppingViewModel(
    getShoppingListsUseCase,
    getShoppingListUseCase,
    addShoppingListUseCase,
    updateShoppingListUseCase,
    deleteShoppingListUseCase,
    deleteIngredientUseCase,
    getIngredientsUseCase,
    getCurrentShoppingListUseCase,
    addIngredientUseCase,
    updateIngredientUseCase
)

fun provideAddIngredientViewModel() = AddIngredientViewModel()

val viewModelModule = module {
    viewModel { provideCategoryViewModel(get(), get()) }
    viewModel { provideRecipeViewModel(get(), get(), get()) }
    viewModel { provideIngredientsViewModel(get()) }
    viewModel {
        provideShoppingViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel { provideAddIngredientViewModel() }
}