package yb.kompose.recipetoshoppinglist.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveIngredientsUseCase

class MainViewModel(
    private val fetchAndSaveIngredientsUseCase: FetchAndSaveIngredientsUseCase,
    private val fetchAndSaveCategoriesUseCase: FetchAndSaveCategoriesUseCase,
) : ViewModel() {

    fun fetchImportantData() = viewModelScope.launch {
        fetchAndSaveIngredientsUseCase()
        fetchAndSaveCategoriesUseCase()
    }

}