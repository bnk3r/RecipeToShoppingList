package yb.kompose.recipetoshoppinglist.features.cooking.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "areas")
data class Area(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)
