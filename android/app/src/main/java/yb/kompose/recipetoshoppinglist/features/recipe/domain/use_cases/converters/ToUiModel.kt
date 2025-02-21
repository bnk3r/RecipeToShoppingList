package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.converters

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Category
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Recipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe

suspend fun Recipe.toUiModel() =
    withContext(Dispatchers.Default) {
        UiRecipe(
            id = id,
            title = name,
            instructions = instructions,
            ingredients = extractIngredients(),
            imgUrl = imageUrl,
            thumbnailUrl = thumbnailUrl,
            recipeUrl = articleUrl,
            category = categoryName,
            area = areaName
        )
    }

suspend fun Recipe.extractIngredients() =
    withContext(Dispatchers.Default) {
        ingredients
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
    }

suspend fun Ingredient.toUiModel() =
    withContext(Dispatchers.Default) {
        UiIngredient(
            name = name,
            amount = "",
            imgUrl = "https://www.themealdb.com/images/ingredients/$name.png",
            thumbnailUrl = "https://www.themealdb.com/images/ingredients/$name-Small.png"
        )
    }

suspend fun Category.toUiModel() =
    withContext(Dispatchers.Default) {
        UiCategory(
            name = name,
            imageUrl = imageUrl
        )
    }