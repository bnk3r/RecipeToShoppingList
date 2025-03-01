package yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states.AddIngredientFromRecipeState

class AddIngredientFromRecipeViewModel : ViewModel() {

    private val _state = MutableStateFlow(AddIngredientFromRecipeState())
    val state = _state.asStateFlow()

}