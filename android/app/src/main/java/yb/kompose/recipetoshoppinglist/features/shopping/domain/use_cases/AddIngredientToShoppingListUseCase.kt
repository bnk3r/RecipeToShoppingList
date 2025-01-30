package yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.repos.ShoppingRepository
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.util.toEntity
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.util.toShoppingIngredient

class AddIngredientToShoppingListUseCase(
    private val shoppingRepository: ShoppingRepository
) {

    suspend operator fun invoke(shoppingList: UiShoppingList, ingredient: UiIngredient) =
        withContext(Dispatchers.IO) {
            shoppingRepository.addShoppingListIngredient(
                ingredient = ingredient.toShoppingIngredient().toEntity().copy(
                    shoppingListId = shoppingList.id
                )
            )
        }

}