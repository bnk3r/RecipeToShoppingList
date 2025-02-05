package yb.kompose.recipetoshoppinglist.features.shopping.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_ingredients")
data class ShoppingListIngredient(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val shoppingListId: Long,
    val name: String,
    val amount: Int,
    val unit: MeasureUnit,
    val imageUrl: String?
)
