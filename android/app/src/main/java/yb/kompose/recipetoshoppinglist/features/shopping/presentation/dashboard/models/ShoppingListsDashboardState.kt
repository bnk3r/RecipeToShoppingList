package yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.models

import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList

data class ShoppingListsDashboardState(
    val shoppingLists: List<UiShoppingList> = emptyList()
)