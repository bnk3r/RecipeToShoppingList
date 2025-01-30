package yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.util

import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.ingredient.Ingredient

fun Ingredient.toEntity() =
    yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient(
        id = idIngredient?.toLong() ?: throw IllegalArgumentException("Ingredient without id"),
        name = strIngredient ?: throw IllegalArgumentException("Ingredient without name."),
        description = strDescription,
        type = strType
    )