package yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states

import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList

data class ShoppingListsScreenState(
    val shoppingLists: List<UiShoppingList> = emptyList(),
    val recipeDetailedId: Long? = null,
    val selectedShoppingListId: Long? = null,
    val isRecipePanelVisible: Boolean = false,
    val isShoppingListPanelVisible: Boolean = false
)