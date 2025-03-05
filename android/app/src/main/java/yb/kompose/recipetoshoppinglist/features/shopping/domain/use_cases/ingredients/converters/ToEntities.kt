package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.converters

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.util.MeasureUnitTypeConverters
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient

suspend fun UiShoppingListIngredient.toEntity(): ShoppingListIngredient =
    withContext(Dispatchers.Default) {
        ShoppingListIngredient(
            id = id,
            shoppingListId = shoppingListId,
            name = name,
            amount = amount,
            unit = unit,
            imageUrl = imageUrl
        )
    }