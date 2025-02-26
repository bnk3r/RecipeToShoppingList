package yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters.AddCurrentShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.models.ShoppingListsDashboardState

class ShoppingListsDashboardViewModel(
    private val getShoppingListsUseCase: GetShoppingListsUseCase,
    private val addCurrentShoppingListUseCase: AddCurrentShoppingListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShoppingListsDashboardState())
    val state = _state.asStateFlow()

    init {
        getShoppingLists()
    }

    private fun getShoppingLists() = getShoppingListsUseCase()
        .map { it.sortByDescendingId() }
        .onEach { updateShoppingLists(it) }
        .launchIn(viewModelScope)

    private fun List<UiShoppingList>.sortByDescendingId() = sortedByDescending { it.id }

    private fun updateShoppingLists(shoppingLists: List<UiShoppingList>) {
        _state.update { it.copy(shoppingLists = shoppingLists) }
    }

    fun addNewListAsCurrent() = viewModelScope.launch {
        addCurrentShoppingListUseCase()
    }

}