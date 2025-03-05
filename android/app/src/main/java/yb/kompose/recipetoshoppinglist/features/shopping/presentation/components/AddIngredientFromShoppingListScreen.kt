package yb.kompose.recipetoshoppinglist.features.shopping.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.DesignButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.item.SelectableListItemContainer
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.picker.LongDropDownMenu
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text.SectionTitle
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text_field.DesignOutlinedTextField
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states.AddIngredientFromShoppingListState

@Composable
fun AddIngredientFromShoppingListScreen(
    state: AddIngredientFromShoppingListState,
    onIngredientSelected: (UiIngredient) -> Unit,
    onIngredientAmountChanged: (String) -> Unit,
    onIngredientUnitChanged: (MeasureUnit) -> Unit,
    onSubmitIngredient: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    var hideKeyboard by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                hideKeyboard = true
            }
            .padding(horizontal = 16.dp),
    ) {
        SectionTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            title = stringResource(R.string.add_ingredient_title)
        )
        Text(
            text = stringResource(
                R.string.selected_ingredient,
                state.ingredientToAdd.selectedIngredient?.name ?: stringResource(R.string.nothing)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(2.dp, MaterialTheme.colorScheme.onSurface),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            when (state.ingredients) {
                null -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                else -> {
                    items(state.ingredients) { ingredient ->
                        SelectableListItemContainer(
                            onClick = { onIngredientSelected(ingredient) },
                            selected = ingredient == state.ingredientToAdd.selectedIngredient,
                            modifier = Modifier.fillMaxWidth(),
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
                                    url = ingredient.imgUrl,
                                    title = ingredient.name
                                )
                                Text(
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = ingredient.name
                                )
                            }
                        }
                    }
                }
            }
        }
        SectionTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            title = stringResource(R.string.quantity)
        )
        DesignOutlinedTextField(
            value = state.ingredientToAdd.amount?.toString() ?: "",
            onValueChanged = onIngredientAmountChanged,
            placeholder = stringResource(R.string.quantity_placeholder),
            hideKeyboard = hideKeyboard,
            onFocusClear = { hideKeyboard = false },
            keyboardType = KeyboardType.Number,
            suffix = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(state.ingredientToAdd.unit.stringRes)
                    )
                    LongDropDownMenu(
                        menuItemData = state.units.map { stringResource(it) },
                        onItemClick = { onIngredientUnitChanged(MeasureUnit.entries[it]) }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        if (state.isIngredientToAddValid) {
            DesignButton(
                onClick = onSubmitIngredient,
                text = stringResource(R.string.add_to_shopping_list),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun AddShoppingIngredientPanelPreview() {
    Scaffold { innerPadding ->
        AddIngredientFromShoppingListScreen(
            state = AddIngredientFromShoppingListState(
                units = MeasureUnit.entries.map { it.stringRes },
                isIngredientToAddValid = true
            ),
            onIngredientAmountChanged = {},
            onIngredientSelected = {},
            onIngredientUnitChanged = {},
            onSubmitIngredient = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}