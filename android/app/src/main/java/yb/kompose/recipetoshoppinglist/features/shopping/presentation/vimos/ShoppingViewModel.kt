package yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.AddIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.DeleteIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.UpdateIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.AddShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.DeleteShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetCurrentShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.UpdateShoppingListUseCase
import java.time.LocalDate

class ShoppingViewModel(
    private val getShoppingListsUseCase: GetShoppingListsUseCase,
    private val getShoppingListUseCase: GetShoppingListUseCase,
    private val addShoppingListUseCase: AddShoppingListUseCase,
    private val updateShoppingListUseCase: UpdateShoppingListUseCase,
    private val deleteShoppingListUseCase: DeleteShoppingListUseCase,
    private val deleteShoppingListIngredientUseCase: DeleteIngredientUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase,
    private val getCurrentShoppingListUseCase: GetCurrentShoppingListUseCase,
    private val addIngredientToShoppingListUseCase: AddIngredientUseCase,
    private val updateShoppingListIngredientUseCase: UpdateIngredientUseCase
) : ViewModel() {

    private var _shoppingLists = MutableStateFlow<List<UiShoppingList>?>(null)
    val shoppingLists = _shoppingLists.asStateFlow()

    private var _currentShoppingList = MutableStateFlow<UiShoppingList?>(null)
    val currentShoppingList = _currentShoppingList.asStateFlow()

    private var _ingredients = MutableStateFlow<List<UiIngredient>?>(null)
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
        viewModelScope.launch {
            getCurrentShoppingListUseCase().collect {
                _currentShoppingList.emit(it)
            }
        }
    }

    fun addNewShoppingList(setAsCurrent: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            addShoppingListUseCase(emptyShoppingList(setAsCurrent))
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

    fun addIngredientToCurrentList(ingredient: UiShoppingListIngredient) =
        viewModelScope.launch(Dispatchers.Default) {
            val currentList = _currentShoppingList.value ?: return@launch
            if (currentList.id != ingredient.shoppingListId) return@launch
            addIngredientToShoppingListUseCase(ingredient)
        }

    fun updateIngredientInCurrentList(updatedIngredient: UiShoppingListIngredient) =
        viewModelScope.launch(Dispatchers.Default) {
            val currentList = _currentShoppingList.value ?: return@launch
            val index = currentList.ingredients.indexOfFirst { it.id == updatedIngredient.id }
            if (index == -1) return@launch
            updateShoppingListIngredientUseCase(updatedIngredient)
        }

    fun removeIngredientFromCurrentList(ingredient: UiShoppingListIngredient) =
        viewModelScope.launch(Dispatchers.Default) {
            val currentList = _currentShoppingList.value ?: return@launch
            deleteShoppingListIngredientUseCase(currentList, ingredient)
        }

    private fun emptyShoppingList(setAsCurrent: Boolean = false): UiShoppingList =
        UiShoppingList(
            id = 0,
            updatedDate = LocalDate.now(),
            ingredients = emptyList(),
            current = setAsCurrent
        )

}