package yb.kompose.recipetoshoppinglist.features.shopping.data.db.models

import androidx.room.TypeConverter

class MeasureUnitTypeConverters {
    @TypeConverter
    fun from(value: MeasureUnit) : String = value.value

    @TypeConverter
    fun to(s: String): MeasureUnit? = MeasureUnit.entries.toList().firstOrNull { it.value == s }
}