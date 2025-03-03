package yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterNotNull
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
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.ui.SelectionIngredient
import java.lang.Exception

class AddIngredientFromShoppingListViewModel(
    private val getIngredientsUseCase: GetIngredientsUseCase,
    private val fetchAndSaveIngredientsUseCase: FetchAndSaveIngredientsUseCase,
    private val addIngredientUseCase: AddIngredientUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddIngredientFromShoppingListState())
    val state = _state.asStateFlow()

    init {
        fetchIngredients()
        updateUnits(MeasureUnit.entries.map { it.displayName })
        getIngredients()
        observeIngredientsForLoadingState()
        observeIngredientsToAddForValidation()
        observeShoppingListIdForIngredientToAdd()
    }

    private fun observeShoppingListIdForIngredientToAdd() = state
        .distinctUntilChangedBy { it.shoppingListId }
        .map { it.shoppingListId }
        .filterNotNull()
        .onEach { updateIngredient(shoppingListId = it) }
        .launchIn(viewModelScope)

    private fun observeIngredientsForLoadingState() = state
        .distinctUntilChangedBy { it.ingredients }
        .map { it.ingredients == null }
        .onEach { updateIngredientsLoadingState(it) }
        .launchIn(viewModelScope)

    private fun getIngredients() = getIngredientsUseCase()
        .map { it.toSelectionIngredients() }
        .onEach { updateIngredients(it) }
        .launchIn(viewModelScope)

    private fun List<UiIngredient>.toSelectionIngredients() = map { ingredient ->
        ingredient.toSelectionIngredient()
    }

    private fun UiIngredient.toSelectionIngredient() = SelectionIngredient(
        name = name,
        imageUrl = imgUrl
    )

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
            amount >= 0 &&
            _state.value.units?.contains(unit) == true &&
            unit != MeasureUnit.NONE.displayName &&
            selectedIngredient.imageUrl?.isNotBlank() == true

    private fun IngredientToAddFromShoppingList.toShoppingIngredient() = UiShoppingListIngredient(
        id = id,
        shoppingListId = shoppingListId ?: throw IllegalArgumentException("No shopping list ID."),
        name = selectedIngredient?.name ?: throw IllegalArgumentException("No name."),
        amount = amount,
        unit = unit,
        imageUrl = selectedIngredient.imageUrl
    )

    private fun updateIngredients(ingredients: List<SelectionIngredient>) {
        _state.update { it.copy(ingredients = ingredients) }
    }

    private fun updateIngredientsLoadingState(isLoading: Boolean) {
        _state.update { it.copy(areIngredientsLoading = isLoading) }
    }

    private fun updateUnits(units: List<String>) {
        _state.update { it.copy(units = units) }
    }

    private fun updateIngredientToAdd(ingredient: IngredientToAddFromShoppingList) {
        _state.update { it.copy(ingredientToAdd = ingredient) }
    }

    private fun updateIngredientToAddValidity(isValid: Boolean) {
        _state.update { it.copy(isIngredientToAddValid = isValid) }
    }

    private fun updateIngredient(
        shoppingListId: Long? = null,
        selectedIngredient: SelectionIngredient? = null,
        amount: Int? = null,
        unit: String? = null,
    ) {
        val ref = _state.value.ingredientToAdd
        updateIngredientToAdd(
            ref.copy(
                shoppingListId = shoppingListId ?: ref.shoppingListId,
                selectedIngredient = selectedIngredient ?: ref.selectedIngredient,
                amount = amount ?: ref.amount,
                unit = unit ?: ref.unit
            )
        )
    }

    private fun sanitizeInputToInt(value: String): Int {
        val amountStr = value.trim()
        if (amountStr.isBlank()) return 0
        return try {
            amountStr.toInt()
        } catch (e: Exception) {
            0
        }
    }

    fun updateIngredientToAdd(ingredient: SelectionIngredient) {
        updateIngredient(selectedIngredient = ingredient)
    }

    fun updateIngredientToAddAmount(amount: String) {
        updateIngredient(amount = sanitizeInputToInt(amount))
    }

    fun updateIngredientToAddUnit(unit: String) {
        updateIngredient(unit = unit)
    }

    fun updateShoppingListId(id: Long) {
        _state.update { it.copy(shoppingListId = id) }
    }

    fun addIngredientToShoppingList() = viewModelScope.launch {
        val ingredient = _state.value.ingredientToAdd
        if (!ingredient.isValid()) {
            throw IllegalStateException("Cannot submit invalid ingredient")
        }
        addIngredientUseCase(ingredient.toShoppingIngredient())
    }

}