package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListWithIngredients
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.util.MeasureUnitTypeConverters
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient

class AddShoppingListUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(shoppingList: UiShoppingList): Long =
        withContext(Dispatchers.IO) {
            shoppingRepository.addShoppingList(shoppingList.toEntity())
        }

    private suspend fun UiShoppingList.toEntity(): ShoppingListWithIngredients =
        withContext(Dispatchers.Default) {
            ShoppingListWithIngredients(
                shoppingList = ShoppingList(
                    id = id,
                    updated = updatedDate,
                    current = current
                ),
                ingredients = ingredients.toEntity()
            )
        }

    private suspend fun List<UiShoppingListIngredient>.toEntity(): List<ShoppingListIngredient> =
        withContext(Dispatchers.Default) {
            map { uiIngredient ->
                ShoppingListIngredient(
                    id = uiIngredient.id,
                    shoppingListId = uiIngredient.shoppingListId,
                    name = uiIngredient.name,
                    amount = uiIngredient.amount,
                    unit = MeasureUnitTypeConverters().to(uiIngredient.unit) ?: MeasureUnit.NONE,
                    imageUrl = uiIngredient.imageUrl
                )
            }
        }

}