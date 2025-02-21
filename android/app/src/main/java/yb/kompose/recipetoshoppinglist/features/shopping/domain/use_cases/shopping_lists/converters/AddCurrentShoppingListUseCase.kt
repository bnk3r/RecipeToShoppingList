package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters

import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.ResetShoppingListsCurrentValueUseCase
import java.time.LocalDate

class AddCurrentShoppingListUseCase(
    private val addShoppingListUseCase: AddShoppingListUseCase,
    private val resetShoppingListsCurrentValueUseCase: ResetShoppingListsCurrentValueUseCase
) {

    suspend operator fun invoke() {
        resetShoppingListsCurrentValueUseCase()
        addShoppingListUseCase(
            shoppingList = UiShoppingList(
                id = 0,
                updatedDate = LocalDate.now(),
                ingredients = emptyList(),
                current = true
            )
        )
    }

}