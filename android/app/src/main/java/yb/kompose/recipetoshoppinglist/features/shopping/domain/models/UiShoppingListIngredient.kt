package yb.kompose.recipetoshoppinglist.features.shopping.domain.models

data class UiShoppingListIngredient(
    val id: Long,
    val shoppingListId: Long,
    val name: String,
    val amount: Int,
    val unit: String,
    val imageUrl: String?
)