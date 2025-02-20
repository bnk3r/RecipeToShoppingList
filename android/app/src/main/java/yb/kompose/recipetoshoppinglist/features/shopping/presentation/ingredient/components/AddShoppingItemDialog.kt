package yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
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
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.models.SelectionIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.vimos.AddIngredientViewModel

@Composable
fun AddShoppingItemDialog(
    viewModel: AddIngredientViewModel = koinViewModel(),
    shoppingListId: Long,
    onDismissRequest: () -> Unit,
    onConfirmation: (UiShoppingListIngredient) -> Unit
) {
    val ingredients = viewModel.ingredients.collectAsStateWithLifecycle().value
    val ingredientsAreLoading = viewModel.ingredientsAreLoading.collectAsStateWithLifecycle().value
    val unitsStr = viewModel.unitsStr.collectAsStateWithLifecycle().value
    val ingredientToAdd = viewModel.ingredientToAdd.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.getIngredients()
    }

    LaunchedEffect(shoppingListId) {
        viewModel.updateIngredient(shoppingListId = shoppingListId)
    }

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

                when (ingredientsAreLoading) {
                    true -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    false -> {
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
                            LongDropDownMenu(
                                menuItemData = ingredients.map { it.name },
                                onItemClick = { ingredientStr ->
                                    viewModel.updateIngredient(
                                        selectedIngredient = SelectionIngredient(
                                            name = ingredientStr,
                                            imageUrl = ingredients.firstOrNull {
                                                it.name == ingredientStr
                                            }?.imageUrl
                                        )
                                    )
                                }
                            )
                        }
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
                            viewModel.updateIngredient(
                                amount = viewModel.amountToString(value)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        suffix = { Text(text = ingredientToAdd.unit) },
                        modifier = Modifier.width(150.dp)
                    )
                    LongDropDownMenu(
                        menuItemData = unitsStr,
                        onItemClick = { unitStr ->
                            viewModel.updateIngredient(unit = unitStr)
                        }
                    )
                }

            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    if (viewModel.isIngredientValid()) {
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