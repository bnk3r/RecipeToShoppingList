package yb.kompose.recipetoshoppinglist.features.recipe.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val imageUrl: String?,
    val description: String?,
    val displayOrderNumber: Int
)
