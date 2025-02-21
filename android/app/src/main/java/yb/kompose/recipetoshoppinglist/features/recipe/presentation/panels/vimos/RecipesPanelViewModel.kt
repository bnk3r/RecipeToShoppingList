package yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase

class RecipesPanelViewModel(
    getCategoriesUseCase: GetRecipeCategoriesUseCase,
    private val getRecipesForCategoryUseCase: GetRecipesForCategoryUseCase,
    private val getRecipesByQueryUseCase: GetRecipesByQueryUseCase
) : ViewModel() {

    val categories = getCategoriesUseCase()
        .map { list ->
            list.firstOrNull()?.let {
                updateSelectedCategory(it)
            }
            list
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    var selectedCategory = MutableStateFlow<UiCategory?>(null)
        private set

    var recipes = MutableStateFlow(emptyList<UiRecipe>())
        private set

    var recipesAreLoading = MutableStateFlow(false)
        private set

    var queryRecipe = MutableStateFlow("")
        private set

    private var getRecipesJob: Job? = null

    fun updateSelectedCategory(category: UiCategory) = viewModelScope.launch {
        // Clear
        recipes.emit(emptyList())
        queryRecipe.emit("")
        getRecipesJob?.cancel()
        // Update
        selectedCategory.emit(category)
        getRecipesJob = viewModelScope.launch {
            recipesAreLoading.emit(true)
            getRecipesForCategoryUseCase(category.name).collect { list ->
                recipesAreLoading.emit(false)
                recipes.emit(list)
            }
        }
    }

    fun updateQueryRecipe(query: String)  = viewModelScope.launch {
        // Clear
        recipes.emit(emptyList())
        selectedCategory.emit(null)
        getRecipesJob?.cancel()
        // Update
        queryRecipe.emit(query)
        if (query.isBlank()) return@launch
        getRecipesJob = viewModelScope.launch {
            recipesAreLoading.emit(true)
            getRecipesByQueryUseCase(query).collect { list ->
                recipesAreLoading.emit(false)
                recipes.emit(list)
            }
        }
    }

    override fun onCleared() {
        getRecipesJob?.cancel()
        super.onCleared()
    }

}