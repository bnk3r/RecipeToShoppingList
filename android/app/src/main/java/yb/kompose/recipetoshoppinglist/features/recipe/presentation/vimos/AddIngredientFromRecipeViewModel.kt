package yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.models.states.AddIngredientFromRecipeState
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.models.ui.IngredientToAddFromRecipe
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.AddIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.shopping_lists.GetShoppingListsUseCase

class AddIngredientFromRecipeViewModel(
    private val getShoppingListsUseCase: GetShoppingListsUseCase,
    private val addIngredientUseCase: AddIngredientUseCase,
    private val getIngredientsUseCase: GetIngredientsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddIngredientFromRecipeState())
    val state = _state.asStateFlow()

    init {
        getCurrentShoppingListId()
        observeIngredientToAddToGetRefIngredients()
        observeStateForValidity()
    }

    private fun getCurrentShoppingListId() =
        getShoppingListsUseCase()
            .flowOn(Dispatchers.IO)
            .map { list -> list.firstOrNull { it.current } }
            .filterNotNull()
            .map { it.id }
            .onEach { updateCurrentShoppingListId(it) }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)

    private fun observeIngredientToAddToGetRefIngredients() = state
        .distinctUntilChangedBy { it.ingredient }
        .map { it.ingredient?.name }
        .filterNotNull()
        .flowOn(Dispatchers.Default)
        .onEach { getRefIngredients(it) }
        .launchIn(viewModelScope)

    private fun observeStateForValidity() = state
        .onEach { updateSubmitValidity(isSubmitValid()) }
        .flowOn(Dispatchers.Default)
        .launchIn(viewModelScope)

    private fun getRefIngredients(name: String) = getIngredientsUseCase()
        .flowOn(Dispatchers.IO)
        .map { list -> list.filter { it.name.contains(name) } }
        .onEach { updateRefIngredients(it) }
        .flowOn(Dispatchers.Default)
        .launchIn(viewModelScope)

    private fun updateSubmitValidity(isValid: Boolean) {
        _state.update { it.copy(isSubmitValid = isValid) }
    }

    private fun updateCurrentShoppingListId(id: Long) {
        _state.update { it.copy(currentShoppingListId = id) }
    }

    private fun updateIngredient(ingredient: IngredientToAddFromRecipe) {
        _state.update { it.copy(ingredient = ingredient) }
    }

    private fun updateRefIngredients(ingredients: List<UiIngredient>) {
        _state.update { it.copy(refIngredients = ingredients) }
    }

    private fun updateIngredient(
        name: String? = null,
        amount: Int? = null,
        unit: MeasureUnit? = null
    ) {
        val ref = _state.value.ingredient
            ?: throw java.lang.IllegalStateException("Ingredient is null.")
        _state.update {
            it.copy(
                ingredient = ref.copy(
                    name = name ?: ref.name,
                    amount = amount ?: ref.amount,
                    unit = unit ?: ref.unit
                )
            )
        }
    }

    private fun isSubmitValid(): Boolean = state.value.ingredient != null
            && state.value.ingredient?.id == 0L
            && state.value.ingredient?.name?.isNotBlank() == true
            && (state.value.ingredient?.amount ?: 0) > 0
            && state.value.currentShoppingListId != null
            && state.value.currentShoppingListId != -1L
            && state.value.refIngredient != null

    private fun UiIngredient.toIngredientToAdd() = IngredientToAddFromRecipe(
        id = 0,
        name = name,
        amount = 0,
        originalAmountDescription = amount,
        unit = MeasureUnit.NONE,
        imgUrl = imgUrl
    )

    fun updateRefIngredient(ingredient: UiIngredient) {
        _state.update { it.copy(refIngredient = ingredient) }
    }

    fun updateAmount(amountStr: String) {
        var amount: Int
        amount = try {
            amountStr.toInt()
        } catch (e: Exception) {
            0
        }
        if (amount < 0) amount = 0
        updateIngredient(amount = amount)
    }

    fun updateUnit(unitStr: String) {
        val unit = MeasureUnit.entries.firstOrNull { it.displayName == unitStr } ?: MeasureUnit.NONE
        updateIngredient(unit = unit)
    }

    fun submitIngredient() = viewModelScope.launch {
        val refIngredient = state.value.refIngredient
            ?: throw IllegalStateException("Ref ingredient to add is null.")
        val ingredientToAdd = state.value.ingredient
            ?: throw IllegalStateException("Ingredient to add is null.")
        val shoppingListId = state.value.currentShoppingListId
            ?: throw IllegalStateException("Current shopping list id not found.")
        if (!state.value.isSubmitValid) {
            throw IllegalStateException("Cannot submit invalid ingredient")
        }
        addIngredientUseCase(
            UiShoppingListIngredient(
                id = 0,
                shoppingListId = shoppingListId,
                name = refIngredient.name,
                amount = ingredientToAdd.amount,
                unit = ingredientToAdd.unit.displayName,
                imageUrl = refIngredient.imgUrl
            )
        )
    }

    fun registerIngredient(ingredient: UiIngredient) {
        updateIngredient(ingredient.toIngredientToAdd())
    }

}