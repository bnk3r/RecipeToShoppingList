package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters.toUiModel

class GetShoppingListsUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(): Flow<List<UiShoppingList>> =
        shoppingRepository.getShoppingLists()
            .map { dbShoppingLists ->
                dbShoppingLists.map { shoppingList -> shoppingList.toUiModel() }
            }
            .flowOn(Dispatchers.Default)

}