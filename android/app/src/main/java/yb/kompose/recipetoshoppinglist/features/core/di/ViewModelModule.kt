package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.activities.MainViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.converters.GetIngredientsByNameUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.vimos.AddIngredientPanelViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos.RecipePanelViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos.RecipesPanelViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screen.vimos.RecipesScreenViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.vimos.ShoppingListsDashboardViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.vimos.AddShoppingIngredientPanelViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.vimos.ShoppingListViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.vimos.ShoppingListsScreenViewModel

fun provideAddIngredientPanelViewModel(
    getIngredientsByNameUseCase: GetIngredientsByNameUseCase
) = AddIngredientPanelViewModel(
    getIngredientsByNameUseCase
)

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::ShoppingListsScreenViewModel)
    viewModelOf(::RecipesScreenViewModel)
    viewModelOf(::ShoppingListsDashboardViewModel)
    viewModelOf(::RecipesPanelViewModel)
    viewModelOf(::RecipePanelViewModel)
    viewModelOf(::ShoppingListViewModel)
    viewModelOf(::AddShoppingIngredientPanelViewModel)
    viewModel { provideAddIngredientPanelViewModel(get()) }
}