package yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.converters.GetIngredientsByNameUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.models.AddIngredientPanelState

class AddIngredientPanelViewModel(
    private val getIngredientsByNameUseCase: GetIngredientsByNameUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddIngredientPanelState())
    val state = _state.asStateFlow()

    init {
        observeIngredientToAddToGetRefIngredients()
        observeRefIngredientsForLoadingState()
    }

    private fun observeIngredientToAddToGetRefIngredients() = state
        .distinctUntilChangedBy { it.ingredientToAdd }
        .map { it.ingredientToAdd?.name }
        .filterNotNull()
        .onEach { getRefIngredients(it) }
        .launchIn(viewModelScope)

    private fun observeRefIngredientsForLoadingState() = state
        .distinctUntilChangedBy { it.refIngredients }
        .map { it.refIngredients == null }
        .onEach { updateRefIngredientsLoadingState(it) }
        .launchIn(viewModelScope)

    private fun getRefIngredients(name: String) = getIngredientsByNameUseCase(name)
        .onEach { updateRefIngredients(it) }
        .launchIn(viewModelScope)

    private fun updateRefIngredients(ingredients: List<UiIngredient>) {
        _state.update { it.copy(refIngredients = ingredients) }
    }

    private fun updateRefIngredientsLoadingState(isLoading: Boolean) {
        _state.update { it.copy(areRefIngredientsLoading = isLoading) }
    }

    fun updateIngredientToAdd(ingredient: UiIngredient) {
        _state.update { it.copy(ingredientToAdd = ingredient) }
    }

    fun updateRefIngredient(ingredient: UiIngredient) {
        _state.update { it.copy(refIngredient = ingredient) }
    }

}