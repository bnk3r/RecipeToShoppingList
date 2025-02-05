package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.util.toEntity

class ReduceIngredientQuantityUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(ingredient: UiShoppingListIngredient) =
        withContext(Dispatchers.IO) {
            val n = ingredient.amount - 1
            if (n < 0) return@withContext
            shoppingRepository.updateShoppingListIngredient(
                ingredient = ingredient.copy(amount = n).toEntity()
            )
        }

}