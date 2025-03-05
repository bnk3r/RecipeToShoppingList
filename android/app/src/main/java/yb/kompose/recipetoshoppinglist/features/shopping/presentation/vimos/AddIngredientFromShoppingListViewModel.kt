package yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.AddIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states.AddIngredientFromShoppingListState
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.ui.IngredientToAddFromShoppingList

class AddIngredientFromShoppingListViewModel(
    private val getIngredientsUseCase: GetIngredientsUseCase,
    private val fetchAndSaveIngredientsUseCase: FetchAndSaveIngredientsUseCase,
    private val addIngredientUseCase: AddIngredientUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddIngredientFromShoppingListState())
    val state = _state.asStateFlow()

    init {
        fetchIngredients()
        getIngredients()
        observeIngredientsToAddForValidation()
    }

    private fun getIngredients() = getIngredientsUseCase()
        .onEach { updateIngredients(it) }
        .launchIn(viewModelScope)

    private fun fetchIngredients() = viewModelScope.launch {
        fetchAndSaveIngredientsUseCase()
    }

    private fun observeIngredientsToAddForValidation() = state
        .distinctUntilChangedBy { it.ingredientToAdd }
        .map { it.ingredientToAdd }
        .map { it.isValid() }
        .onEach { updateIngredientToAddValidity(it) }
        .launchIn(viewModelScope)

    private fun IngredientToAddFromShoppingList.isValid() = id == 0L &&
            shoppingListId != -1L &&
            selectedIngredient?.name?.isNotBlank() == true &&
            amount != null && amount > 0 &&
            selectedIngredient.imgUrl?.isNotBlank() == true

    private fun updateIngredients(ingredients: List<UiIngredient>) {
        _state.update { it.copy(ingredients = ingredients) }
    }

    private fun updateIngredientToAddValidity(isValid: Boolean) {
        _state.update { it.copy(isIngredientToAddValid = isValid) }
    }

    private fun sanitizeInputToInt(value: String): Int? {
        val amountStr = value.trim()
        if (amountStr.isBlank()) {
            return null
        }
        return try {
            val a = amountStr.toInt()
            a
        } catch (e: Exception) {
            null
        }
    }

    fun updateIngredientToAdd(ingredient: UiIngredient?) {
        val ref = state.value.ingredientToAdd
        _state.update { it.copy(ingredientToAdd = ref.copy(selectedIngredient = ingredient)) }
    }

    fun updateIngredientToAddAmount(amount: String) {
        val ref = state.value.ingredientToAdd
        _state.update { it.copy(ingredientToAdd = ref.copy(amount = sanitizeInputToInt(amount))) }
    }

    fun updateIngredientToAddUnit(unit: MeasureUnit) {
        val ref = state.value.ingredientToAdd
        _state.update { it.copy(ingredientToAdd = ref.copy(unit = unit)) }
    }

    fun updateShoppingListId(id: Long) {
        val ref = state.value.ingredientToAdd
        _state.update { it.copy(ingredientToAdd = ref.copy(shoppingListId = id)) }
    }

    fun addIngredientToShoppingList() = viewModelScope.launch {
        val ingredient = _state.value.ingredientToAdd
        if (!ingredient.isValid()) {
            throw IllegalStateException("Cannot submit invalid ingredient")
        }
        addIngredientUseCase(
            UiShoppingListIngredient(
                id = ingredient.id,
                shoppingListId = ingredient.shoppingListId
                    ?: throw IllegalArgumentException("No shopping list ID."),
                name = ingredient.selectedIngredient?.name
                    ?: throw IllegalArgumentException("No name."),
                amount = ingredient.amount
                    ?: throw IllegalArgumentException("No amount."),
                unit = ingredient.unit,
                imageUrl = ingredient.selectedIngredient.imgUrl
            )
        )
    }

}