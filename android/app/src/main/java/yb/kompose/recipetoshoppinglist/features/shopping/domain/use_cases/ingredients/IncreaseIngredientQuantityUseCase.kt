package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.util.MeasureUnitTypeConverters
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.converters.toEntity

class IncreaseIngredientQuantityUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(ingredient: UiShoppingListIngredient) =
        withContext(Dispatchers.IO) {
            if (ingredient.amount >= Int.MAX_VALUE) return@withContext
            shoppingRepository.updateShoppingListIngredient(
                ingredient = ingredient.copy(amount = ingredient.amount + 1).toEntity()
            )
        }

}