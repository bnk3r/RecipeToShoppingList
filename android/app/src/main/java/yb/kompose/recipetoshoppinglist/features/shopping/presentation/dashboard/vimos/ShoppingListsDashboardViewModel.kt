package yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters.AddCurrentShoppingListUseCase

class ShoppingListsDashboardViewModel(
    getShoppingListsUseCase: GetShoppingListsUseCase,
    private val addCurrentShoppingListUseCase: AddCurrentShoppingListUseCase
) : ViewModel() {

    val shoppingLists: StateFlow<List<UiShoppingList>> =
        getShoppingListsUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addNewListAsCurrent() = viewModelScope.launch {
        addCurrentShoppingListUseCase()
    }

}