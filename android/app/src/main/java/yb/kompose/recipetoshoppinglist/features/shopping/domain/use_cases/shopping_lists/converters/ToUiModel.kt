package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListWithIngredients
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.converters.toUiModel

suspend fun ShoppingListWithIngredients.toUiModel(): UiShoppingList =
    withContext(Dispatchers.Default) {
        UiShoppingList(
            id = shoppingList.id,
            updatedDate = shoppingList.updated,
            ingredients = ingredients.map { it.toUiModel() },
            current = shoppingList.current
        )
    }