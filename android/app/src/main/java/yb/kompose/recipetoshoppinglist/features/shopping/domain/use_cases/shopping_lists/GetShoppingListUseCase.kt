package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters.toUiModel

class GetShoppingListUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    operator fun invoke(id: Long): Flow<UiShoppingList?> =
        shoppingRepository.getShoppingList(id)
            .map { shoppingList ->
                shoppingList?.toUiModel()
            }

}