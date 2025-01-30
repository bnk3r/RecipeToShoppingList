package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.util.toUiModel

class GetShoppingListIngredientUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(id: Long) : Flow<UiShoppingListIngredient?> =
        withContext(Dispatchers.IO) {
            shoppingRepository.getShoppingListIngredient(id).map { ingredient ->
                ingredient?.toUiModel()
            }
        }

}