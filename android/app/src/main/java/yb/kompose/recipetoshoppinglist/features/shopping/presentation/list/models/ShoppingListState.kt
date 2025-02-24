package yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.models

import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList

data class ShoppingListState(
    val shoppingListId: Long? = null,
    val shoppingList: UiShoppingList? = null,
    val isShoppingListLoading: Boolean = false,
    val isAddIngredientPanelVisible: Boolean = false
)