package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient

class GetShoppingListIngredientUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(id: Long): Flow<UiShoppingListIngredient?> =
        withContext(Dispatchers.IO) {
            shoppingRepository.getShoppingListIngredient(id).map { ingredient ->
                ingredient?.toUiModel()
            }
        }

    private suspend fun ShoppingListIngredient.toUiModel() =
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

}