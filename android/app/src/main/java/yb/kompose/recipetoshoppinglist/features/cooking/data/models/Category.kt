package yb.kompose.recipetoshoppinglist.features.cooking.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val imageUrl: String?,
    val thumbnailUrl: String?,
    val description: String?,
    val displayOrderNumber: Int
)
