package yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.models

import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList

data class ShoppingListScreenState(
    val shoppingListId: Long? = null,
    val shoppingList: UiShoppingList? = null,
    val isShoppingListLoading: Boolean = false,
    val isAddIngredientPanelVisible: Boolean = false
)