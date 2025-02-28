package yb.kompose.recipetoshoppinglist.features.core.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yb.kompose.recipetoshoppinglist.activities.MainViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos.RecipePanelViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos.RecipesPanelViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screen.vimos.RecipeScreenViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screen.vimos.RecipesScreenViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.AddShoppingIngredientPanelViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingListScreenViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingListsScreenViewModel

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::ShoppingListsScreenViewModel)
    viewModelOf(::RecipesScreenViewModel)
    viewModelOf(::RecipeScreenViewModel)
    viewModelOf(::RecipesPanelViewModel)
    viewModelOf(::RecipePanelViewModel)
    viewModelOf(::ShoppingListScreenViewModel)
    viewModelOf(::AddShoppingIngredientPanelViewModel)
}