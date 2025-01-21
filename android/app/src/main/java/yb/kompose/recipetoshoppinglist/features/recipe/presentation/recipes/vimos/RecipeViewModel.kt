package yb.kompose.recipetoshoppinglist.features.recipe.presentation.recipes.vimos

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase

class RecipeViewModel(
    private val getRecipesForCategoryUseCase: GetRecipesForCategoryUseCase
) : ViewModel() {

    suspend fun getRecipesForCategory(categoryName: String): Flow<List<UiRecipe>> {
        return getRecipesForCategoryUseCase(categoryName = categoryName)
    }

}