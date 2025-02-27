package yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.models.ShoppingListsScreenState

class ShoppingListsScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(ShoppingListsScreenState())
    val state = _state.asStateFlow()

    init {
        observeRecipeIdForPanelVisibility()
        observeShoppingListIdForPanelVisibility()
    }

    private fun observeRecipeIdForPanelVisibility() = state
        .distinctUntilChangedBy { it.recipeDetailedId }
        .map { it.recipeDetailedId != null }
        .onEach { updateRecipePanelVisibility(it) }
        .launchIn(viewModelScope)

    private fun observeShoppingListIdForPanelVisibility() = state
        .distinctUntilChangedBy { it.selectedShoppingListId }
        .map { it.selectedShoppingListId != null }
        .onEach { updateShoppingListPanelVisibility(it) }
        .launchIn(viewModelScope)

    private fun updateRecipePanelVisibility(isVisible: Boolean) {
        _state.update { it.copy(isRecipePanelVisible = isVisible) }
    }

    private fun updateShoppingListPanelVisibility(isVisible: Boolean) {
        _state.update { it.copy(isShoppingListPanelVisible = isVisible) }
    }

    fun updateSelectedShoppingListId(id: Long?) {
        _state.update { it.copy(selectedShoppingListId = id) }
    }

    fun updateRecipeDetailedId(id: Long?) {
        _state.update { it.copy(recipeDetailedId = id) }
    }

}