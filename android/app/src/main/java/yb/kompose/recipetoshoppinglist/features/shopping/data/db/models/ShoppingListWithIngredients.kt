package yb.kompose.recipetoshoppinglist.features.shopping.data.db.models

import androidx.room.Embedded
import androidx.room.Relation


data class ShoppingListWithIngredients(
    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "id",
        entityColumn = "shoppingListId"
    )
    val ingredients: List<ShoppingListIngredient>
)