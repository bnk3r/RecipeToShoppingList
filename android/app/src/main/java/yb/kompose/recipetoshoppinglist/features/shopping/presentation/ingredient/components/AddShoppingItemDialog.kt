package yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.picker.LongDropDownMenu
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient

@Composable
fun AddShoppingItemDialog(
    ingredients: List<UiIngredient>,
    onDismissRequest: () -> Unit,
    onConfirmation: (UiIngredient) -> Unit
) {

    var ingredientsStr by remember(ingredients) {
        mutableStateOf(ingredients.map { ingredient ->
            ingredient.name
        })
    }

    var selectedIngredient by remember { mutableStateOf<UiIngredient?>(null) }

    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.ShoppingBasket,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = stringResource(R.string.add_ingredient_title)
            )
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = selectedIngredient?.name ?: stringResource(R.string.no_selection),
                    style = MaterialTheme.typography.titleLarge
                )
                LongDropDownMenu(ingredientsStr) { ingredientStr ->
                    val index = ingredients.indexOfFirst { ingredient ->
                        ingredient.name == ingredientStr
                    }
                    selectedIngredient = ingredients[index]
                }
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    selectedIngredient?.let {
                        onConfirmation(it)
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.confirm)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(
                    text = stringResource(R.string.dismiss)
                )
            }
        }
    )
}