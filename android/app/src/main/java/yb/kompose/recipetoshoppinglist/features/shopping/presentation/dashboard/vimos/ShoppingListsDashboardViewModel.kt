package yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.vimos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.core.domain.models.FlowState
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.ResetShoppingListsCurrentValueUseCase
import java.time.LocalDate

class ShoppingListsDashboardViewModel(
    private val getShoppingListsUseCase: GetShoppingListsUseCase,
    private val addShoppingListUseCase: AddShoppingListUseCase,
    private val resetShoppingListsCurrentValueUseCase: ResetShoppingListsCurrentValueUseCase
) : ViewModel() {

    private var _shoppingListsState = MutableStateFlow(FlowState.IDLE)
    private val _shoppingLists = MutableStateFlow(emptyList<UiShoppingList>())

    val shoppingListsState = _shoppingListsState.asStateFlow()
    val shoppingLists = _shoppingLists.asStateFlow()

    fun getShoppingLists() = viewModelScope.launch {
        _shoppingListsState.emit(FlowState.LOADING)
        getShoppingListsUseCase()
            .catch { err ->
                Log.e("getShoppingLists", err.message ?: "Error.")
                _shoppingLists.emit(emptyList())
                _shoppingListsState.emit(FlowState.ERROR)
            }
            .collect { shoppingLists ->
                _shoppingLists.emit(shoppingLists.sortedByDescending { it.id })
                _shoppingListsState.emit(FlowState.SUCCESS)
            }
    }

    fun addNewListAsCurrent() = viewModelScope.launch {
        resetShoppingListsCurrentValueUseCase()
        addShoppingListUseCase(
            shoppingList = UiShoppingList(
                id = 0,
                updatedDate = LocalDate.now(),
                ingredients = emptyList(),
                current = true
            )
        )
    }

}