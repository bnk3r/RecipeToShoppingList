package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.converters

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient

suspend fun ShoppingListIngredient.toUiModel(): UiShoppingListIngredient =
    withContext(Dispatchers.Default) {
        UiShoppingListIngredient(
            id = id,
            shoppingListId = shoppingListId,
            name = name,
            amount = amount,
            unit = unit.displayName,
            imageUrl = imageUrl
        )
    }