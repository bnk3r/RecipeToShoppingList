package yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.picker.LongDropDownMenu
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.models.SelectionIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.vimos.AddIngredientViewModel

@Composable
fun AddShoppingItemDialog(
    addIngredientViewModel: AddIngredientViewModel = koinViewModel(),
    shoppingListId: Long,
    ingredients: List<UiIngredient>,
    onDismissRequest: () -> Unit,
    onConfirmation: (UiShoppingListIngredient) -> Unit
) {

    LaunchedEffect(ingredients) {
        addIngredientViewModel.addIngredients(ingredients)
        addIngredientViewModel.registerShoppingList(shoppingListId)
    }

    val selectionIngredients =
        addIngredientViewModel.selectionIngredients.collectAsStateWithLifecycle().value
    val unitsStr = addIngredientViewModel.unitsStr.collectAsStateWithLifecycle().value
    val ingredientToAdd = addIngredientViewModel.ingredientToAdd.collectAsStateWithLifecycle().value

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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = ingredientToAdd.selectedIngredient?.name
                            ?: stringResource(R.string.no_selection),
                        style = MaterialTheme.typography.titleLarge
                    )
                    LongDropDownMenu(selectionIngredients.map { it.name }) { ingredientStr ->
                        addIngredientViewModel.updateIngredient(
                            selectedIngredient = SelectionIngredient(
                                name = ingredientStr,
                                imageUrl = selectionIngredients.firstOrNull {
                                    it.name == ingredientStr
                                }?.imageUrl
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = ingredientToAdd.amount.toString(),
                        onValueChange = { value ->
                            addIngredientViewModel.updateIngredient(
                                amount = addIngredientViewModel.amountToString(value)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        suffix = { Text(text = ingredientToAdd.unit) },
                        modifier = Modifier.width(150.dp)
                    )
                    LongDropDownMenu(unitsStr) { unitStr ->
                        addIngredientViewModel.updateIngredient(unit = unitStr)
                    }
                }

            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    if (addIngredientViewModel.isIngredientValid()) {
                        onConfirmation(ingredientToAdd.toShoppingIngredient())
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