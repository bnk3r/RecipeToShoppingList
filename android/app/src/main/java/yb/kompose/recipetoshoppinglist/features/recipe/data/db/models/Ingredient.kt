package yb.kompose.recipetoshoppinglist.features.recipe.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String?,
    val type: String?
)