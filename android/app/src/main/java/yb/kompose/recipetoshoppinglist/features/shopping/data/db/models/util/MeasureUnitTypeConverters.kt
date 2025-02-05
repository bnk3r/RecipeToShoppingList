package yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.util

import androidx.room.TypeConverter
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit

class MeasureUnitTypeConverters {
    @TypeConverter
    fun from(value: MeasureUnit): String = value.displayName

    @TypeConverter
    fun to(s: String): MeasureUnit? =
        MeasureUnit.entries.toList().firstOrNull { it.displayName == s }
}