package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.util.MeasureUnitTypeConverters
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient

class DeleteShoppingListIngredientUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(
        shoppingList: UiShoppingList,
        ingredient: UiShoppingListIngredient
    ) = withContext(Dispatchers.IO) {
        if (shoppingList.id != ingredient.shoppingListId) return@withContext
        shoppingRepository.deleteShoppingListIngredient(ingredient.toEntity())
    }

    private suspend fun UiShoppingListIngredient.toEntity(): ShoppingListIngredient =
        withContext(Dispatchers.Default) {
            ShoppingListIngredient(
                id = id,
                shoppingListId = shoppingListId,
                name = name,
                amount = amount,
                unit = MeasureUnitTypeConverters().to(unit) ?: MeasureUnit.NONE,
                imageUrl = imageUrl
            )
        }

}