package yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.vimos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.converters.AddCurrentShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.components.ShoppingListsDashboardState

class ShoppingListsDashboardViewModel(
    getShoppingListsUseCase: GetShoppingListsUseCase,
    private val addCurrentShoppingListUseCase: AddCurrentShoppingListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShoppingListsDashboardState())
    val state = _state.asStateFlow()

    init {
        getShoppingListsUseCase()
            .map { shoppingLists ->
                shoppingLists.sortedByDescending { it.id }
            }
            .onEach { shoppingLists ->
                Log.d("COLLECT", "Shopping lists collected (size=${shoppingLists.size})")
                _state.update {
                    it.copy(
                        shoppingLists = shoppingLists
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun addNewListAsCurrent() = viewModelScope.launch {
        addCurrentShoppingListUseCase()
    }

}