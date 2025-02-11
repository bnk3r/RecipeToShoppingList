package yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase

class RecipesPanelViewModel(
    private val getCategoriesUseCase: GetRecipeCategoriesUseCase,
    private val fetchAndSaveCategoriesUseCase: FetchAndSaveCategoriesUseCase,
    private val getRecipesForCategoryUseCase: GetRecipesForCategoryUseCase,
    private val getRecipesByQueryUseCase: GetRecipesByQueryUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow(listOf<UiCategory>())
    private val _selectedCategory = MutableStateFlow<UiCategory?>(null)
    private val _recipes = MutableStateFlow(emptyList<UiRecipe>())
    private val _recipesAreLoading = MutableStateFlow(false)

    private var getRecipesJob: Job? = null

    val categories = _categories.asStateFlow()
    val selectedCategory = _selectedCategory.asStateFlow()
    val recipes = _recipes.asStateFlow()
    var recipesAreLoading = _recipesAreLoading.asStateFlow()

    fun getCategories() = viewModelScope.launch {
        getCategoriesUseCase().collect { categories ->
            _categories.emit(categories)
            _selectedCategory.emit(categories.firstOrNull())
        }
    }

    fun fetchAndSaveCategories() = viewModelScope.launch {
        fetchAndSaveCategoriesUseCase()
    }

    fun getRecipesForCategory(name: String) {
        getRecipesJob?.cancel()
        getRecipesJob = viewModelScope.launch {
            _recipes.emit(emptyList())
            _recipesAreLoading.emit(true)
            getRecipesForCategoryUseCase(name).collect { recipes ->
                _recipesAreLoading.emit(false)
                _recipes.emit(recipes)
            }
        }
    }

    fun getRecipesByQuery(query: String) {
        getRecipesJob?.cancel()
        getRecipesJob = viewModelScope.launch {
            _recipes.emit(emptyList())
            _recipesAreLoading.emit(true)
            getRecipesByQueryUseCase(query).collect { recipes ->
                _recipesAreLoading.emit(false)
                _recipes.emit(recipes)
            }
        }
    }

    fun updateSelectedCategory(category: UiCategory) = viewModelScope.launch {
        _selectedCategory.emit(category)
    }

    override fun onCleared() {
        getRecipesJob?.cancel()
        super.onCleared()
    }

}