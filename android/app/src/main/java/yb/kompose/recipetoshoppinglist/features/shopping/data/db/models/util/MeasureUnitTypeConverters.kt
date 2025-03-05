package yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.util

import androidx.room.TypeConverter
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit

class MeasureUnitTypeConverters {
    @TypeConverter
    fun from(value: MeasureUnit): Int = value.stringRes

    @TypeConverter
    fun to(stringRes: Int): MeasureUnit? =
        MeasureUnit.entries.firstOrNull { it.stringRes == stringRes }
}