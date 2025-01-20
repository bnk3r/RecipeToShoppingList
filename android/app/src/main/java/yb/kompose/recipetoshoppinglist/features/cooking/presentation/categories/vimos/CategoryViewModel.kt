package yb.kompose.recipetoshoppinglist.features.cooking.presentation.categories.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.cooking.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.cooking.domain.use_cases.GetRecipeCategoriesUseCase

class CategoryViewModel(
    private val getCategoryUseCase: GetRecipeCategoriesUseCase
): ViewModel() {

    private val _categories = MutableStateFlow(listOf<UiCategory>())
    val categories = _categories.asStateFlow()

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getCategoryUseCase().collect { categories ->
                _categories.emit(categories)
            }
        }
    }

}