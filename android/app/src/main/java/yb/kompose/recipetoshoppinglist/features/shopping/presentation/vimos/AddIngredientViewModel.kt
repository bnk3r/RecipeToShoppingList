package yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.models.IngredientToAdd
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.models.SelectionIngredient

class AddIngredientViewModel : ViewModel() {

    private val _ingredientToAdd = MutableStateFlow(IngredientToAdd())
    val ingredientToAdd = _ingredientToAdd.asStateFlow()

    private val _selectionIngredients = MutableStateFlow(emptyList<SelectionIngredient>())
    val selectionIngredients = _selectionIngredients.asStateFlow()

    private val _unitsStr = MutableStateFlow(MeasureUnit.entries.map { it.displayName })
    val unitsStr = _unitsStr.asStateFlow()

    fun updateIngredient(
        shoppingListId: Long? = null,
        selectedIngredient: SelectionIngredient? = null,
        amount: Int? = null,
        unit: String? = null,
    ) = viewModelScope.launch {
        _ingredientToAdd.emit(
            _ingredientToAdd.value.copy(
                id = _ingredientToAdd.value.id,
                shoppingListId = shoppingListId ?: _ingredientToAdd.value.shoppingListId,
                selectedIngredient = selectedIngredient
                    ?: _ingredientToAdd.value.selectedIngredient,
                amount = amount ?: _ingredientToAdd.value.amount,
                unit = unit ?: _ingredientToAdd.value.unit
            )
        )
    }

    fun resetIngredient() = viewModelScope.launch {
        _ingredientToAdd.emit(IngredientToAdd())
    }

    fun isIngredientValid() =
        _ingredientToAdd.value.id == 0L &&
                _ingredientToAdd.value.shoppingListId != -1L &&
                _ingredientToAdd.value.selectedIngredient?.name?.isNotBlank() == true &&
                _ingredientToAdd.value.amount >= 0 &&
                _unitsStr.value.contains(_ingredientToAdd.value.unit) &&
                _ingredientToAdd.value.selectedIngredient?.imageUrl?.isNotBlank() == true

    fun addIngredients(ingredients: List<UiIngredient>) = viewModelScope.launch {
        _selectionIngredients.emit(ingredients.map { ingredient ->
            SelectionIngredient(
                name = ingredient.name,
                imageUrl = ingredient.imgUrl
            )
        })
    }

    fun registerShoppingList(shoppingListId: Long) = viewModelScope.launch {
        updateIngredient(shoppingListId = shoppingListId)
    }

    fun amountToString(value: String): Int {
        val amountStr = value.trim()
        if (amountStr.isBlank()) return 0
        return Regex("[^A-Za-z0-9 ]").replace(amountStr, "").toInt()
    }

}