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
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.models.ShoppingScreenState

class ShoppingScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(ShoppingScreenState())
    val state = _state.asStateFlow()

    init {
        state
            .distinctUntilChangedBy { it.recipeDetailedId }
            .map { it.recipeDetailedId != null }
            .onEach { isRecipeDetailedNotNull ->
                _state.update {
                    it.copy(
                        isRecipePanelVisible = isRecipeDetailedNotNull
                    )
                }
            }
            .launchIn(viewModelScope)

        state
            .distinctUntilChangedBy { it.selectedShoppingListId }
            .map { it.selectedShoppingListId != null }
            .onEach { isSelectedShoppingListIdNotNull ->
                _state.update {
                    it.copy(
                        isShoppingListPanelVisible = isSelectedShoppingListIdNotNull
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateSelectedShoppingListId(id: Long?) {
        _state.update {
            it.copy(
                selectedShoppingListId = id
            )
        }
    }

    fun updateRecipeDetailedId(id: Long?) {
        _state.update {
            it.copy(
                recipeDetailedId = id
            )
        }
    }

}