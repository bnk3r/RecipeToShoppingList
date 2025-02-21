package yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.vimos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.FetchAndSaveIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.recipe.domain.use_cases.GetIngredientsUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.domain.use_cases.ingredients.AddIngredientUseCase
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.models.IngredientToAdd
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.models.SelectionIngredient

class AddIngredientViewModel(
    private val getIngredientsUseCase: GetIngredientsUseCase,
    private val fetchAndSaveIngredientsUseCase: FetchAndSaveIngredientsUseCase,
    private val addIngredientUseCase: AddIngredientUseCase
) : ViewModel() {

    private val _ingredients = MutableStateFlow(emptyList<SelectionIngredient>())
    private val _ingredientsAreLoading = MutableStateFlow(false)
    private val _ingredientToAdd = MutableStateFlow(IngredientToAdd())
    private val _unitsStr = MutableStateFlow(MeasureUnit.entries.map { it.displayName })

    var ingLoading = MutableStateFlow(false)
        private set

    val ing = getIngredientsUseCase()
        .also {
            ingLoading.value = true
        }
        .map { list ->
            list.map { element ->
                SelectionIngredient(
                    name = element.name,
                    imageUrl = element.imgUrl
                )
            }
        }
        .flowOn(Dispatchers.Default)
        .also {
            ingLoading.value = false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val ingredientsAreLoading = _ingredientsAreLoading.asStateFlow()
    val ingredientToAdd = _ingredientToAdd.asStateFlow()
    val ingredients = _ingredients.asStateFlow()
    val unitsStr = _unitsStr.asStateFlow()

    fun fetchIngredients() = viewModelScope.launch {
        fetchAndSaveIngredientsUseCase()
    }

    fun getIngredients() = viewModelScope.launch {
        _ingredientsAreLoading.emit(true)
        getIngredientsUseCase().collect { reqIngredients ->
            if (reqIngredients.isEmpty()) {
                fetchAndSaveIngredientsUseCase()
                return@collect
            }
            // convert to Selectable ingredients
            addIngredients(reqIngredients)
            _ingredientsAreLoading.emit(false)
        }
    }

    fun updateIngredient(
        shoppingListId: Long? = null,
        selectedIngredient: SelectionIngredient? = null,
        amount: Int? = null,
        unit: String? = null,
    ) = viewModelScope.launch {
        val ref = _ingredientToAdd.value
        _ingredientToAdd.emit(
            ref.copy(
                id = ref.id,
                shoppingListId = shoppingListId ?: ref.shoppingListId,
                selectedIngredient = selectedIngredient ?: ref.selectedIngredient,
                amount = amount ?: ref.amount,
                unit = unit ?: ref.unit
            )
        )
    }

    fun isIngredientValid() =
        _ingredientToAdd.value.id == 0L &&
                _ingredientToAdd.value.shoppingListId != -1L &&
                _ingredientToAdd.value.selectedIngredient?.name?.isNotBlank() == true &&
                _ingredientToAdd.value.amount >= 0 &&
                _unitsStr.value.contains(_ingredientToAdd.value.unit) &&
                _ingredientToAdd.value.selectedIngredient?.imageUrl?.isNotBlank() == true

    fun amountToString(value: String): Int {
        val amountStr = value.trim()
        if (amountStr.isBlank()) return 0
        return Regex("[^A-Za-z0-9 ]").replace(amountStr, "").toInt()
    }

    fun addIngredientToShoppingList() =
        viewModelScope.launch {
            addIngredientUseCase(_ingredientToAdd.value.toShoppingIngredient())
        }

    private fun addIngredients(ingredients: List<UiIngredient>) = viewModelScope.launch {
        _ingredients.emit(ingredients.map { ingredient ->
            SelectionIngredient(
                name = ingredient.name,
                imageUrl = ingredient.imgUrl
            )
        })
    }

}