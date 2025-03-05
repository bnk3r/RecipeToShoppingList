package yb.kompose.recipetoshoppinglist.features.shopping.data.db.models

import androidx.annotation.StringRes
import yb.kompose.recipetoshoppinglist.R

enum class MeasureUnit(@StringRes val stringRes: Int) {
    NONE(R.string.unit_none),
    GRAM(R.string.unit_gram),
    KILOGRAM(R.string.unit_kilogram),
    MILLILITER(R.string.unit_milliliter),
    LITER(R.string.unit_liter)
}