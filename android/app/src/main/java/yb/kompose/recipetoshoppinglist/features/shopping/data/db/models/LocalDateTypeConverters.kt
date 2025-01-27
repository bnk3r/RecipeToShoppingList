package yb.kompose.recipetoshoppinglist.features.shopping.data.db.models

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class LocalDateTypeConverters {

    companion object {
        const val DB_DATE_FORMAT_PATTERN = "dd MM yyyy"
    }

    @TypeConverter
    fun from(date: LocalDate?): String? =
        date?.format(DateTimeFormatter.ofPattern(DB_DATE_FORMAT_PATTERN))

    @TypeConverter
    fun to(s: String?): LocalDate? =
        LocalDate.parse(s, DateTimeFormatter.ofPattern(DB_DATE_FORMAT_PATTERN))

}