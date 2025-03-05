package yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipeCategoriesUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesByQueryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetRecipesForCategoryUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.models.states.RecipesScreenState

class RecipesScreenViewModel(
    private val getCategoriesUseCase: GetRecipeCategoriesUseCase,
    private val getRecipesForCategoryUseCase: GetRecipesForCategoryUseCase,
    private val getRecipesByQueryUseCase: GetRecipesByQueryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RecipesScreenState())
    val state = _state.asStateFlow()

    init {
        getCategories()
        observeCategoriesForSelectedCategory()
        observeRecipesForLoadingState()
        observeSelectedCategoryToGetRecipes()
        observeSearchQueryToGetRecipes()
        observeSearchQueryToResetSelectedCategory()
    }

    override fun onCleared() {
        _state.value.getRecipesJob?.cancel()
        super.onCleared()
    }

    private fun getCategories() = getCategoriesUseCase()
        .onEach { updateCategories(it) }
        .launchIn(viewModelScope)

    private fun observeCategoriesForSelectedCategory() = state
        .distinctUntilChangedBy { it.categories }
        .map { it.categories.firstOrNull() }
        .filterNotNull()
        .onEach { updateSelectedCategory(it) }
        .launchIn(viewModelScope)

    private fun observeRecipesForLoadingState() = state
        .distinctUntilChangedBy { it.recipes }
        .map { it.recipes == null }
        .onEach { updateAreRecipesLoading(it) }
        .launchIn(viewModelScope)

    private fun observeSelectedCategoryToGetRecipes() = state
        .distinctUntilChangedBy { it.selectedCategory }
        .map { it.selectedCategory?.name }
        .filterNotNull()
        .onEach { cancelGetRecipesJob() }
        .onEach { deleteRecipes() }
        .onEach { deleteSearchQuery() }
        .map { getRecipesForCategory(it) }
        .onEach { updateGetRecipesJob(it) }
        .launchIn(viewModelScope)

    private fun observeSearchQueryToGetRecipes() = state
        .distinctUntilChangedBy { it.searchQuery }
        .map { it.searchQuery }
        .filter { it.isNotBlank() }
        .onEach { cancelGetRecipesJob() }
        .onEach { deleteRecipes() }
        .onEach { deleteSelectedCategory() }
        .map { getRecipesByQuery(it) }
        .onEach { updateGetRecipesJob(it) }
        .launchIn(viewModelScope)

    /**
     * Reset selected category to first on empty query.
     */
    private fun observeSearchQueryToResetSelectedCategory() = state
        .distinctUntilChangedBy { it.searchQuery }
        .map { it.searchQuery }
        .filter { it.isBlank() }
        .onEach { resetSelectedCategoryToFirst() }
        .launchIn(viewModelScope)

    private suspend fun getRecipesForCategory(name: String) =
        getRecipesForCategoryUseCase(name)
            .onEach { updateRecipes(it) }
            .launchIn(viewModelScope)

    private suspend fun getRecipesByQuery(query: String) =
        getRecipesByQueryUseCase(query)
            .onEach { updateRecipes(it) }
            .launchIn(viewModelScope)

    private fun updateRecipes(recipes: List<UiRecipe>) {
        _state.update { it.copy(recipes = recipes) }
    }

    private fun updateGetRecipesJob(job: Job) {
        _state.update { it.copy(getRecipesJob = job) }
    }

    private fun cancelGetRecipesJob() {
        _state.value.getRecipesJob?.cancel()
    }

    private fun updateAreRecipesLoading(isLoading: Boolean) {
        _state.update { it.copy(areRecipesLoading = isLoading) }
    }

    private fun updateCategories(categories: List<UiCategory>) {
        _state.update { it.copy(categories = categories) }
    }

    private fun deleteSelectedCategory() {
        _state.update { it.copy(selectedCategory = null) }
    }

    private fun deleteSearchQuery() {
        _state.update { it.copy(searchQuery = "") }
    }

    private fun deleteRecipes() {
        _state.update { it.copy(recipes = null) }
    }

    private fun resetSelectedCategoryToFirst() {
        _state.update { it.copy(selectedCategory = _state.value.categories.firstOrNull()) }
    }

    fun updateSelectedCategory(category: UiCategory) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun updateQueryRecipe(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

}