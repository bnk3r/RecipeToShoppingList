package yb.kompose.recipetoshoppinglist.features.shopping.domain.models

import java.time.LocalDate

data class UiShoppingList(
    val id: Long,
    val updatedDate: LocalDate,
    val ingredients: List<UiShoppingListIngredient>
)
