package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListWithIngredients
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.util.toUiModel

class GetShoppingListsUseCase(
    private val shoppingRepository: ShoppingRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): Flow<List<UiShoppingList>> = withContext(defaultDispatcher) {
        shoppingRepository.getShoppingLists().toUiModel()
    }

    private fun Flow<List<ShoppingListWithIngredients>>.toUiModel(): Flow<List<UiShoppingList>> =
        map { shoppingListsWithIngredients ->
            shoppingListsWithIngredients.map { shoppingListWithIngredients ->
                shoppingListWithIngredients.toUiModel()
            }
        }.flowOn(defaultDispatcher)
}