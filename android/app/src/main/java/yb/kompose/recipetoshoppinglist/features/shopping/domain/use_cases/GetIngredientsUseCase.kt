package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.util.toUiModel

class GetIngredientsUseCase(
    private val shoppingRepository: ShoppingRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): Flow<List<UiShoppingListIngredient>> =
        withContext(defaultDispatcher) {
            shoppingRepository.getIngredients().map { ingredient ->
                ingredient.toUiModel()
            }
        }

}