package yb.kompose.recipetoshoppinglist.features.recipe.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "areas")
data class Area(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)
