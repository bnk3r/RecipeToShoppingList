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
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.DeleteShoppingListIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.DeleteShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.UpdateShoppingListUseCase
import java.time.LocalDate

class ShoppingViewModel(
    private val getShoppingListsUseCase: GetShoppingListsUseCase,
    private val getShoppingListUseCase: GetShoppingListUseCase,
    private val addShoppingListUseCase: AddShoppingListUseCase,
    private val updateShoppingListUseCase: UpdateShoppingListUseCase,
    private val deleteShoppingListUseCase: DeleteShoppingListUseCase,
    private val deleteShoppingListIngredientUseCase: DeleteShoppingListIngredientUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase
) : ViewModel() {

    private var _shoppingLists = MutableStateFlow<List<UiShoppingList>?>(null)
    val shoppingLists = _shoppingLists.asStateFlow()

    private var _currentShoppingList = MutableStateFlow<UiShoppingList?>(null)
    val currentShoppingList = _currentShoppingList.asStateFlow()

    private var _ingredients = MutableStateFlow<List<UiShoppingListIngredient>?>(null)
    val ingredients = _ingredients.asStateFlow()

    init {
        // Expose shopping lists immediately
        viewModelScope.launch {
            getShoppingListsUseCase().collect {
                _shoppingLists.emit(it)
            }
        }
        viewModelScope.launch {
            getIngredientsUseCase().collect {
                _ingredients.emit(it)
            }
        }
    }

    fun addNewShoppingList() =
        viewModelScope.launch(Dispatchers.IO) {
            addShoppingListUseCase(emptyShoppingList())
        }

    suspend fun getShoppingListById(id: Long): Flow<UiShoppingList?> =
        withContext(Dispatchers.IO) {
            getShoppingListUseCase(id)
        }

    fun updateShoppingList(shoppingList: UiShoppingList) =
        viewModelScope.launch(Dispatchers.IO) {
            updateShoppingListUseCase(shoppingList)
        }

    fun deleteShoppingList(shoppingList: UiShoppingList) = viewModelScope.launch(Dispatchers.IO) {
        deleteShoppingListUseCase(shoppingList)
    }

    fun setCurrentShoppingList(shoppingList: UiShoppingList) {
        _currentShoppingList.value = shoppingList
    }

    fun addIngredientToCurrentList(ingredient: UiShoppingListIngredient) =
        viewModelScope.launch(Dispatchers.Default) {
            val currentList = _currentShoppingList.value ?: return@launch
            val ingredients = currentList.ingredients.toMutableList()
            ingredients.add(ingredient.copy(shoppingListId = currentList.id))
            updateShoppingListUseCase(currentList.copy(ingredients = ingredients))
        }

    fun updateIngredientInCurrentList(updatedIngredient: UiShoppingListIngredient) =
        viewModelScope.launch(Dispatchers.Default) {
            val currentList = _currentShoppingList.value ?: return@launch
            val index = currentList.ingredients.indexOfFirst { it.id == updatedIngredient.id }
            if (index == -1) return@launch
            val ingredients = currentList.ingredients.toMutableList()
            ingredients[index] = updatedIngredient
            updateShoppingListUseCase(currentList.copy(ingredients = ingredients))
        }

    fun removeIngredientFromCurrentList(ingredient: UiShoppingListIngredient) =
        viewModelScope.launch(Dispatchers.Default) {
            val currentList = _currentShoppingList.value ?: return@launch
            deleteShoppingListIngredientUseCase(currentList, ingredient)
        }

    private fun emptyShoppingList(): UiShoppingList =
        UiShoppingList(
            id = 0,
            updatedDate = LocalDate.now(),
            ingredients = emptyList()
        )

}