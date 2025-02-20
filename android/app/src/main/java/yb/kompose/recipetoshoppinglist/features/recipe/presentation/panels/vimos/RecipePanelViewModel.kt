package yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase

class RecipePanelViewModel(
    private val getRecipeDetailedUseCase: GetRecipeDetailedUseCase
) : ViewModel() {

    private val _recipe = MutableStateFlow<UiRecipe?>(null)
    private val _recipeIsLoading = MutableStateFlow(false)

    val recipe = _recipe.asStateFlow()
    val recipeIsLoading = _recipeIsLoading.asStateFlow()

    private var getRecipeJob: Job? = null

    fun getRecipe(id: Long) {
        getRecipeJob = viewModelScope.launch {
            _recipeIsLoading.emit(true)
            getRecipeDetailedUseCase(id).collect { recipe ->
                _recipe.emit(recipe)
                _recipeIsLoading.emit(false)
            }
        }
    }

    fun clearRecipeJob() = viewModelScope.launch {
        getRecipeJob?.cancel()
        _recipe.emit(null)
        _recipeIsLoading.emit(false)
    }

}