package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListWithIngredients
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient

class GetShoppingListUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(id: Long): Flow<UiShoppingList?> =
        withContext(Dispatchers.IO) {
            shoppingRepository.getShoppingList(id).map { shoppingList ->
                shoppingList?.toUiModel()
            }
        }

    private suspend fun ShoppingListWithIngredients.toUiModel(): UiShoppingList =
        withContext(Dispatchers.Default) {
            UiShoppingList(
                id = shoppingList.id,
                updatedDate = shoppingList.updated,
                ingredients = ingredients.toUiModel(),
                current = shoppingList.current
            )
        }

    private suspend fun List<ShoppingListIngredient>.toUiModel(): List<UiShoppingListIngredient> =
        withContext(Dispatchers.Default) {
            map { ingredient ->
                UiShoppingListIngredient(
                    id = ingredient.id,
                    shoppingListId = ingredient.shoppingListId,
                    name = ingredient.name,
                    amount = ingredient.amount,
                    unit = ingredient.unit.displayName,
                    imageUrl = ingredient.imageUrl
                )
            }
        }

}