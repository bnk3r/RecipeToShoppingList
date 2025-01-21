package yb.kompose.recipetoshoppinglist.features.recipe.domain.models.util

import yb.kompose.recipetoshoppinglist.features.recipe.data.api.models.Meal
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe

fun Meal.toUiModel() = UiRecipe(
    id = idMeal.toInt(),
    title = strMeal ?: "",
    instructions = strInstructions ?: "",
    ingredients = this.extractIngredients(),
    imgUrl = strMealThumb ?: "",
    thumbnailUrl = "${strMealThumb ?: ""}/preview",
    recipeUrl = strYoutube ?: "",
    category = strCategory ?: "",
    area = strArea ?: ""
)

fun Meal.extractIngredients(): List<UiIngredient> {
    val ingredients = mutableListOf<UiIngredient>()

    if (!strIngredient1.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient1,
                amount = strMeasure1 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient1.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient1-Small.png"
            )
        )
    }
    if (!strIngredient2.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient2,
                amount = strMeasure2 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient2.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient2-Small.png"
            )
        )
    }
    if (!strIngredient3.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient3,
                amount = strMeasure3 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient3.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient3-Small.png"
            )
        )
    }
    if (!strIngredient4.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient4,
                amount = strMeasure4 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient4.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient4-Small.png"
            )
        )
    }
    if (!strIngredient5.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient5,
                amount = strMeasure5 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient5.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient5-Small.png"
            )
        )
    }
    if (!strIngredient6.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient6,
                amount = strMeasure6 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient6.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient6-Small.png"
            )
        )
    }
    if (!strIngredient7.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient7,
                amount = strMeasure7 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient7.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient7-Small.png"
            )
        )
    }
    if (!strIngredient8.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient8,
                amount = strMeasure8 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient8.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient8-Small.png"
            )
        )
    }
    if (!strIngredient9.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient9,
                amount = strMeasure9 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient9.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient9-Small.png"
            )
        )
    }
    if (!strIngredient10.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient10,
                amount = strMeasure10 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient10.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient10-Small.png"
            )
        )
    }
    if (!strIngredient11.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient11,
                amount = strMeasure11 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient11.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient11-Small.png"
            )
        )
    }
    if (!strIngredient12.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient12,
                amount = strMeasure12 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient12.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient12-Small.png"
            )
        )
    }
    if (!strIngredient13.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient13,
                amount = strMeasure13 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient13.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient13-Small.png"
            )
        )
    }
    if (!strIngredient14.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient14,
                amount = strMeasure14 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient14.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient14-Small.png"
            )
        )
    }
    if (!strIngredient15.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient15,
                amount = strMeasure15 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient15.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient15-Small.png"
            )
        )
    }
    if (!strIngredient16.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient16,
                amount = strMeasure16 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient16.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient16-Small.png"
            )
        )
    }
    if (!strIngredient17.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient17,
                amount = strMeasure17 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient17.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient17-Small.png"
            )
        )
    }
    if (!strIngredient18.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient18,
                amount = strMeasure18 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient18.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient18-Small.png"
            )
        )
    }
    if (!strIngredient19.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient19,
                amount = strMeasure19 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient19.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient19-Small.png"
            )
        )
    }
    if (!strIngredient20.isNullOrBlank()) {
        ingredients.add(
            UiIngredient(
                name = strIngredient20,
                amount = strMeasure20 ?: "",
                imgUrl = "www.themealdb.com/images/ingredients/$strIngredient20.png",
                thumbnailUrl = "www.themealdb.com/images/ingredients/$strIngredient20-Small.png"
            )
        )
    }
    return ingredients
}