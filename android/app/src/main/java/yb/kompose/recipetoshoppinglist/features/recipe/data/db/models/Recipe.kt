package yb.kompose.recipetoshoppinglist.features.recipe.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val categoryName: String?,
    val areaName: String?,
    val instructions: String?,
    val imageUrl: String?,
    val thumbnailUrl: String?,
    val tags: String?,
    val youtubeVideoUrl: String?,
    val ingredients: String?,
    // ie. "365:2 medium,221:2 tbs chopped"
    // where 365 and 221 are ingredients ids and measures are placed after semicolons
    val articleUrl: String?
)
