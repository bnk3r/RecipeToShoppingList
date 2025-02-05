package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.util.toEntity

class AddIngredientToShoppingListUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(
        ingredient: UiShoppingListIngredient
    ) = withContext(Dispatchers.IO) {
        shoppingRepository.addShoppingListIngredient(
            ingredient = ingredient.toEntity()
        )
    }

}