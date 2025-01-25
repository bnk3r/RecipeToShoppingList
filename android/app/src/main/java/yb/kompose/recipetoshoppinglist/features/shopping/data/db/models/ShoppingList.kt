package yb.kompose.recipetoshoppinglist.features.shopping.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "shopping_list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val updated: LocalDate
)
