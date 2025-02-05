package yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.data.db.models.Ingredient
import yb.kompose.recipetoshoppinglist.features.recipe.data.repos.RecipeRepository
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient

class GetIngredientsUseCase(
    private val recipeRepository: RecipeRepository
) {

    suspend operator fun invoke(): Flow<List<UiIngredient>> =
        withContext(Dispatchers.IO) {
            recipeRepository.getIngredients().map { ingredients ->
                ingredients.map { ingredient ->
                    ingredient.toUiModel()
                }
            }
        }

    private suspend fun Ingredient.toUiModel() =
        withContext(Dispatchers.Default) {
            UiIngredient(
                name = name,
                amount = "",
                imgUrl = "https://www.themealdb.com/images/ingredients/$name.png",
                thumbnailUrl = "https://www.themealdb.com/images/ingredients/$name-Small.png"
            )
        }

}