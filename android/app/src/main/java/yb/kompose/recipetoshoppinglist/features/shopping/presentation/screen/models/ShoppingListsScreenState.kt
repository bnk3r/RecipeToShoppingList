package yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.models

data class ShoppingListsScreenState(
    val recipeDetailedId: Long? = null,
    val selectedShoppingListId: Long? = null,
    val isRecipePanelVisible: Boolean = false,
    val isShoppingListPanelVisible: Boolean = false
)