package yb.kompose.recipetoshoppinglist.features.recipe.domain.models.util

import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Category
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Recipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe

fun Recipe.toUiModel() = UiRecipe(
    id = id.toInt(),
    title = name,
    instructions = instructions,
    ingredients = extractIngredients(),
    imgUrl = imageUrl,
    thumbnailUrl = thumbnailUrl,
    recipeUrl = articleUrl,
    category = categoryName,
    area = areaName
)

fun Recipe.extractIngredients() = ingredients
    ?.split(",")
    ?.filter { it.isNotEmpty() }
    ?.filter { it.split(":").size == 2 }
    ?.map { part ->
        val splits = part.split(":")
        UiIngredient(
            name = splits[0],
            amount = splits[1],
            imgUrl = "https://www.themealdb.com/images/ingredients/${splits[0]}.png",
            thumbnailUrl = "https://www.themealdb.com/images/ingredients/${splits[0]}-Small.png"
        )
    }
    ?: emptyList()

fun Category.toUiModel() = UiCategory(
    name = name,
    imageUrl = imageUrl
)