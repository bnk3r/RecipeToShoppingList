package yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.converters.GetIngredientsByNameUseCase

class AddIngredientPanelViewModel(
    private val getIngredientsByNameUseCase : GetIngredientsByNameUseCase
) : ViewModel() {

    var refIngredients = MutableStateFlow(emptyList<UiIngredient>())

    var refIngredientsAreLoading = MutableStateFlow(false)
        private set

    var refIngredient = MutableStateFlow<UiIngredient?>(null)

    fun collectRefIngredients(name: String) = viewModelScope.launch {
        refIngredientsAreLoading.emit(true)
        getIngredientsByNameUseCase(name).collect { ingredients ->
            refIngredients.emit(ingredients)
            refIngredientsAreLoading.emit(false)
        }
    }

    fun updateRefIngredient(ingredient: UiIngredient) = viewModelScope.launch {
        refIngredient.emit(ingredient)
    }

}