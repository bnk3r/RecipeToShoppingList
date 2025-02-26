package yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.DeleteIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.models.ShoppingListState

class ShoppingListViewModel(
    private val getShoppingListUseCase: GetShoppingListUseCase,
    private val deleteIngredientUseCase: DeleteIngredientUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShoppingListState())
    val state = _state.asStateFlow()

    init {
        observeShoppingListIdToGetShoppingList()
        observeShoppingListForLoadingState()
    }

    private fun observeShoppingListIdToGetShoppingList() = state
        .distinctUntilChangedBy { it.shoppingListId }
        .map { it.shoppingListId }
        .filterNotNull()
        .filter { it != -1L }
        .onEach { getShoppingList(it) }
        .launchIn(viewModelScope)

    private fun observeShoppingListForLoadingState() = state
        .distinctUntilChangedBy { it.shoppingList }
        .map { it.shoppingList == null }
        .onEach { updateIsShoppingListLoading(it) }
        .launchIn(viewModelScope)

    private fun getShoppingList(id: Long) =
        getShoppingListUseCase(id)
            .filterNotNull()
            .onEach { updateShoppingList(it) }
            .launchIn(viewModelScope)

    private fun updateShoppingList(shoppingList: UiShoppingList) {
        _state.update { it.copy(shoppingList = shoppingList) }
    }

    private fun updateIsShoppingListLoading(isLoading: Boolean) {
        _state.update { it.copy(isShoppingListLoading = isLoading) }
    }

    fun updateShoppingListId(id: Long) {
        _state.update { it.copy(shoppingListId = id) }
    }

    fun deleteIngredient(ingredient: UiShoppingListIngredient) = viewModelScope.launch {
        val shoppingList = _state.value.shoppingList ?: return@launch
        deleteIngredientUseCase(shoppingList, ingredient)
    }

    fun updateAddIngredientPanelVisibility(visible: Boolean) {
        _state.update { it.copy(isAddIngredientPanelVisible = visible) }
    }
}