package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters.toEntity

class UpdateShoppingListUseCase(
    private val shoppingRepository: ShoppingRepository
) {
    suspend operator fun invoke(shoppingList: UiShoppingList) =
        withContext(Dispatchers.IO) {
            shoppingRepository.updateShoppingList(shoppingList.toEntity())
        }

}