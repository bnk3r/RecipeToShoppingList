package yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeDetailedUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.models.RecipePanelState

class RecipePanelViewModel(
    private val getRecipeDetailedUseCase: GetRecipeDetailedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RecipePanelState())
    val state = _state.asStateFlow()

    init {
        observeRecipeIdToGetRecipe()
        observeRecipeForLoadingState()
        observeIngredientToAddForPanelVisibility()
    }

    private fun observeRecipeIdToGetRecipe() = state
        .distinctUntilChangedBy { it.recipeId }
        .map { it.recipeId }
        .filterNotNull()
        .map { getRecipe(it) }
        .onEach { updateGetRecipeJob(it) }
        .launchIn(viewModelScope)

    private fun observeRecipeForLoadingState() = state
        .distinctUntilChangedBy { it.recipe }
        .map { it.recipe == null }
        .onEach { updateIsRecipeLoading(it) }
        .launchIn(viewModelScope)

    private fun observeIngredientToAddForPanelVisibility() = state
        .distinctUntilChangedBy { it.ingredientToAdd }
        .map { it.ingredientToAdd != null }
        .onEach { updateIsAddIngredientPanelVisible(it) }
        .launchIn(viewModelScope)

    private suspend fun getRecipe(id: Long) = getRecipeDetailedUseCase(id)
        .filterNotNull()
        .onEach { updateRecipe(it) }
        .launchIn(viewModelScope)

    private fun updateRecipe(recipe: UiRecipe) {
        _state.update { it.copy(recipe = recipe) }
    }

    private fun updateIsRecipeLoading(isLoading: Boolean) {
        _state.update { it.copy(isRecipeLoading = isLoading) }
    }

    private fun updateGetRecipeJob(job: Job) {
        _state.update { it.copy(getRecipeJob = job) }
    }

    private fun updateIsAddIngredientPanelVisible(visible: Boolean) {
        _state.update { it.copy(isAddIngredientPanelVisible = visible) }
    }

    fun updateRecipeId(id: Long) {
        _state.update { it.copy(recipeId = id) }
    }

    fun updateIngredientToAdd(ingredient: UiIngredient?) {
        _state.update { it.copy(ingredientToAdd = ingredient) }
    }

    fun clear() {
        _state.value.getRecipeJob?.cancel()
    }

}