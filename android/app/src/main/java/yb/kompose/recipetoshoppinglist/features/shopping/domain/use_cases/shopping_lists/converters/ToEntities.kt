package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListWithIngredients
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.converters.toEntity

suspend fun UiShoppingList.toEntity(): ShoppingListWithIngredients =
    withContext(Dispatchers.Default) {
        ShoppingListWithIngredients(
            shoppingList = ShoppingList(
                id = id,
                updated = updatedDate,
                current = current
            ),
            ingredients = ingredients.map { it.toEntity() }
        )
    }
