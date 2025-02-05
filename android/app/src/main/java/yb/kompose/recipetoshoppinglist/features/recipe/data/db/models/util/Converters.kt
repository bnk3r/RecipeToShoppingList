package yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.util

import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.ingredient.Ingredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient

fun Ingredient.toEntity() =
    yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient(
        id = idIngredient?.toLong() ?: throw IllegalArgumentException("Ingredient without id"),
        name = strIngredient ?: throw IllegalArgumentException("Ingredient without name."),
        description = strDescription,
        type = strType
    )

fun yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient.toUiModel() =
    UiIngredient(
        name = name,
        amount = "",
        imgUrl = "https://www.themealdb.com/images/ingredients/$name.png",
        thumbnailUrl = "https://www.themealdb.com/images/ingredients/$name-Small.png"
    )