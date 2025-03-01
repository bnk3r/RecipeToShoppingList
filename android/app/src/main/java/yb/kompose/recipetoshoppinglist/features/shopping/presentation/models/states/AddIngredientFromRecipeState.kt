package yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states

import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.ui.IngredientToAdd

data class AddIngredientFromRecipeState(
    val ingredient: IngredientToAdd? = null
)
