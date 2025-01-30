package yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.util

import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.ShoppingListIngredient

fun Ingredient.toShoppingIngredient() =
    ShoppingListIngredient(
        id = id,
        shoppingListId = -1,
        name = name,
        amount = 0.0,
        unit = MeasureUnit.BLANK,
        imageUrl = "https://www.themealdb.com/images/ingredients/${name}.png"
    )