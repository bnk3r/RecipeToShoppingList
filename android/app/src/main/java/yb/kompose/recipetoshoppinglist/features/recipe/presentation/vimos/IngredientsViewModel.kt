package yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveIngredientsUseCase

class IngredientsViewModel(
    private val fetchAndSaveIngredientsUseCase: FetchAndSaveIngredientsUseCase
) : ViewModel() {

    fun fetchAndSaveIngredients() = viewModelScope.launch {
        fetchAndSaveIngredientsUseCase()
    }

}