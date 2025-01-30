package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.DeleteShoppingListIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.DeleteShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.UpdateShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingViewModel

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

fun provideShoppingViewModel(
    getShoppingListsUseCase: GetShoppingListsUseCase,
    getShoppingListUseCase: GetShoppingListUseCase,
    addShoppingListUseCase: AddShoppingListUseCase,
    updateShoppingListUseCase: UpdateShoppingListUseCase,
    deleteShoppingListUseCase: DeleteShoppingListUseCase,
    deleteShoppingListIngredientUseCase: DeleteShoppingListIngredientUseCase,
    getIngredientsUseCase: GetIngredientsUseCase
) = ShoppingViewModel(
    getShoppingListsUseCase,
    getShoppingListUseCase,
    addShoppingListUseCase,
    updateShoppingListUseCase,
    deleteShoppingListUseCase,
    deleteShoppingListIngredientUseCase,
    getIngredientsUseCase
)

val viewModelModule = module {
    viewModel { provideCategoryViewModel(get()) }
    viewModel { provideRecipeViewModel(get(), get(), get()) }
    viewModel { provideShoppingViewModel(get(), get(), get(), get(), get(), get(), get()) }
}