package yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.AddIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.DeleteIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListUseCase

class ShoppingListViewModel(
    private val getShoppingListUseCase: GetShoppingListUseCase,
    private val addIngredientUseCase: AddIngredientUseCase,
    private val deleteIngredientUseCase: DeleteIngredientUseCase
) : ViewModel() {

    private val _shoppingList = MutableStateFlow<UiShoppingList?>(null)
    private val _shoppingListIsLoading = MutableStateFlow(false)

    val shoppingList = _shoppingList.asStateFlow()
    val shoppingListIsLoading = _shoppingListIsLoading.asStateFlow()

    private var getShoppingListJob: Job? = null

    fun getShoppingList(id: Long) {
        getShoppingListJob = viewModelScope.launch {
            _shoppingListIsLoading.emit(true)
            getShoppingListUseCase(id).collect { list ->
                _shoppingList.emit(list)
                _shoppingListIsLoading.emit(false)
            }
        }
    }

    fun addIngredient(ingredient: UiShoppingListIngredient) = viewModelScope.launch {
        _shoppingList.value?.let { list ->
            addIngredientUseCase(ingredient.copy(shoppingListId = list.id))
        }
    }

    fun deleteIngredient(ingredient: UiShoppingListIngredient) = viewModelScope.launch {
        _shoppingList.value?.let { list ->
            deleteIngredientUseCase(list, ingredient)
        }
    }

    fun clearJobs() = viewModelScope.launch {
        getShoppingListJob?.cancel()
        _shoppingList.emit(null)
        _shoppingListIsLoading.emit(false)
    }

}