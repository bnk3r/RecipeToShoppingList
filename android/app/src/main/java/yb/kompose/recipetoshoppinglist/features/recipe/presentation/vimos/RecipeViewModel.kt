package yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase

class RecipeViewModel(
    private val getRecipesForCategoryUseCase: GetRecipesForCategoryUseCase,
    private val getRecipesByQueryUseCase: GetRecipesByQueryUseCase,
    private val getRecipeDetailedUseCase: GetRecipeDetailedUseCase
) : ViewModel() {

    suspend fun getRecipesForCategory(categoryName: String): Flow<List<UiRecipe>> {
        return getRecipesForCategoryUseCase(categoryName = categoryName)
    }

    suspend fun getRecipesByQuery(query: String): Flow<List<UiRecipe>> {
        return getRecipesByQueryUseCase(query)
    }

    suspend fun getRecipeDetailed(id: Long): Flow<UiRecipe?> {
        return getRecipeDetailedUseCase(id)
    }

}