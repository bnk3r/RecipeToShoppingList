package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists

import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository

class ResetShoppingListsCurrentValueUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(): Unit = shoppingRepository.resetShoppingListsCurrentValue()

}