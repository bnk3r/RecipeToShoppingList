package yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.DeleteShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.UpdateShoppingListUseCase
import java.time.LocalDate

class ShoppingViewModel(
    private val getShoppingListsUseCase: GetShoppingListsUseCase,
    private val getShoppingListUseCase: GetShoppingListUseCase,
    private val addShoppingListUseCase: AddShoppingListUseCase,
    private val updateShoppingListUseCase: UpdateShoppingListUseCase,
    private val deleteShoppingListUseCase: DeleteShoppingListUseCase
) : ViewModel() {

    private var _shoppingLists = MutableStateFlow(emptyList<UiShoppingList>())
    val shoppingLists = _shoppingLists.asStateFlow()

    init {
        // Expose shopping lists immediately
        viewModelScope.launch {
            getShoppingListsUseCase().collect {
                _shoppingLists.emit(it)
            }
        }
    }

    fun addNewShoppingList() = viewModelScope.launch(Dispatchers.IO) {
        addShoppingListUseCase(emptyShoppingList())
    }

    suspend fun getShoppingListById(id: Long) : Flow<UiShoppingList?> = withContext(Dispatchers.IO) {
        getShoppingListUseCase(id)
    }

    fun updateShoppingList(shoppingList: UiShoppingList) = viewModelScope.launch(Dispatchers.IO) {
        updateShoppingListUseCase(shoppingList)
    }

    fun deleteShoppingList(shoppingList: UiShoppingList) = viewModelScope.launch(Dispatchers.IO) {
        deleteShoppingListUseCase(shoppingList)
    }



    private fun emptyShoppingList(): UiShoppingList =
        UiShoppingList(
            id = 0,
            updatedDate = LocalDate.now(),
            ingredients = emptyList()
        )

}