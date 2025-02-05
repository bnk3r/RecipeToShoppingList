package yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase

class CategoryViewModel(
    private val getCategoryUseCase: GetRecipeCategoriesUseCase,
    private val fetchAndSaveCategoriesUseCase: FetchAndSaveCategoriesUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow(listOf<UiCategory>())
    val categories = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            getCategoryUseCase().collect { categories ->
                _categories.emit(categories)
            }
        }
    }

    fun fetchAndSaveCategories() = viewModelScope.launch {
        fetchAndSaveCategoriesUseCase()
    }

}