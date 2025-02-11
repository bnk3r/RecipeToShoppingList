package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository

class ResetShoppingListsCurrentValueUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke() : Unit =
        withContext(Dispatchers.IO) {
            shoppingRepository.resetShoppingListsCurrentValue()
        }

}