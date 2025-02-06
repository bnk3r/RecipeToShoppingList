package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters.toUiModel

class GetShoppingListsUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(): Flow<List<UiShoppingList>> =
        withContext(Dispatchers.IO) {
            shoppingRepository.getShoppingLists()
                .map { shoppingListsWithIngredients ->
                    shoppingListsWithIngredients.map { shoppingListWithIngredients ->
                        shoppingListWithIngredients.toUiModel()
                    }
                }
        }

}