package yb.kompose.recipetoshoppinglist.features.recipe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.DesignButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.item.SelectableListItemContainer
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.picker.LongDropDownMenu
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text_field.DesignOutlinedTextField
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.models.states.AddIngredientFromRecipeState
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit

@Composable
fun AddIngredientFromRecipeScreen(
    state: AddIngredientFromRecipeState,
    onRefIngredientChanged: (UiIngredient) -> Unit,
    onAmountChanged: (String) -> Unit,
    onUnitChanged: (MeasureUnit) -> Unit,
    onClickSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    var hideKeyboard by remember { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                hideKeyboard = true
            }
    ) {

        item {
            Text(
                text = stringResource(R.string.add_to_shopping_list),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        state.ingredient?.name?.let { name ->
            item {
                Text(
                    text = stringResource(R.string.selected_ingredient, name),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        state.ingredient?.originalAmountDescription?.let { amountDescription ->
            item {
                Text(
                    text = stringResource(
                        R.string.selected_ingredient_amount_description,
                        amountDescription
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }

        item {
            Text(
                text = stringResource(R.string.add_ingredient_title),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface)
            ) {
                when (state.refIngredients) {
                    null -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    else -> {
                        items(state.refIngredients) { refIngredient ->
                            SelectableListItemContainer(
                                onClick = { onRefIngredientChanged(refIngredient) },
                                selected = refIngredient == state.refIngredient,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                ) {
                                    CachedAsyncImage(
                                        modifier = Modifier
                                            .width(36.dp)
                                            .aspectRatio(1f),
                                        url = refIngredient.imgUrl,
                                        title = refIngredient.name
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text = refIngredient.name
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            DesignOutlinedTextField(
                value = state.ingredient?.amount?.toString() ?: "",
                onValueChanged = onAmountChanged,
                placeholder = stringResource(R.string.quantity_placeholder),
                hideKeyboard = hideKeyboard,
                onFocusClear = { hideKeyboard = false },
                keyboardType = KeyboardType.Number,
                suffix = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(
                                state.ingredient?.unit?.stringRes ?: MeasureUnit.NONE.stringRes
                            )

                        )
                        LongDropDownMenu(
                            menuItemData = state.measureUnits.map { stringResource(it) },
                            onItemClick = { onUnitChanged(MeasureUnit.entries[it]) }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 24.dp)
            )
        }

        if (state.isSubmitValid) {
            item {
                DesignButton(
                    onClick = onClickSubmit,
                    text = stringResource(R.string.add_this_ingredient_to_current_shopping_list),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}