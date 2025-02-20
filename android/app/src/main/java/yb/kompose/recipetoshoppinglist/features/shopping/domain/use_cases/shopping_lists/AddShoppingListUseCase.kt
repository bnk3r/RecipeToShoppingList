package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists

import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters.toEntity

class AddShoppingListUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(shoppingList: UiShoppingList): Long =
        shoppingRepository.addShoppingList(shoppingList.toEntity())

}