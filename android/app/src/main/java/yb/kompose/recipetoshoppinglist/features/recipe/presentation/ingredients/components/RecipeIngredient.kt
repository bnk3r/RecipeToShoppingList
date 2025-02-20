package yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.translation.FrenchTranslatedText
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient

@Composable
fun RecipeIngredient(
    modifier: Modifier = Modifier,
    ingredient: UiIngredient,
    addToShoppingList: (ingredient: UiIngredient) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FrenchTranslatedText(
            text = "${ingredient.name} - ${ingredient.amount}",
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ShoppingBasket,
            contentDescription = stringResource(R.string.add_to_shopping_list),
            modifier = Modifier.clickable {
                addToShoppingList(ingredient)
            }
        )
    }
}