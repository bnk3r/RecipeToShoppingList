package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.util.toEntity

class DeleteShoppingListIngredientUseCase(
    private val shoppingRepository: ShoppingRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(
        shoppingList: UiShoppingList,
        ingredient: UiShoppingListIngredient
    ) = withContext(defaultDispatcher) {
        if (shoppingList.id != ingredient.shoppingListId) return@withContext
        shoppingRepository.deleteShoppingListIngredient(ingredient.toEntity())
    }

}