package yb.kompose.recipetoshoppinglist.features.shopping.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.FAB
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.screen.FrontLayerContainer
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text.SectionTitle
import yb.kompose.recipetoshoppinglist.features.core.presentation.models.FrontLayerPosition
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states.ShoppingListScreenState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ShoppingListScreen(
    state: ShoppingListScreenState,
    onClickAddIngredient: (Long) -> Unit,
    onDeleteIngredient: (UiShoppingListIngredient) -> Unit,
    modifier: Modifier = Modifier
) {
    FrontLayerContainer(
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SectionTitle(
                        title = stringResource(R.string.shopping_list)
                    )
                }
                state.shoppingList?.updatedDate?.let { date ->
                    item {
                        Text(
                            text = stringResource(
                                R.string.last_update_on,
                                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            ),
                            modifier = Modifier.padding(bottom = 32.dp)
                        )
                    }
                }
                state.shoppingList?.ingredients?.let { ingredients ->
                    items(ingredients) { ingredient ->
                        DeleteableIngredient(
                            ingredient = ingredient,
                            onDelete = { onDeleteIngredient(ingredient) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        },
        frontLayerContent = {
            state.shoppingList?.id?.let { id ->
                FAB(
                    onClick = {
                        onClickAddIngredient(id)
                    },
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_to_shopping_list)
                )
            }
        },
        frontLayerContentPosition = FrontLayerPosition.BOTTOM_END,
        frontLayerPadding = 32.dp,
        modifier = modifier
    )
}

@Preview(showSystemUi = true, backgroundColor = 0xffffffff)
@Composable
private fun ShoppingListPreview() {
    Scaffold { innerPadding ->
        ShoppingListScreen(
            state = ShoppingListScreenState(
                shoppingList = UiShoppingList(
                    id = 0,
                    updatedDate = LocalDate.now(),
                    ingredients = listOf(
                        UiShoppingListIngredient(
                            id = 0,
                            shoppingListId = 0,
                            name = "Ingredient 1",
                            amount = 100,
                            unit = MeasureUnit.GRAM.displayName,
                            imageUrl = null
                        ),
                        UiShoppingListIngredient(
                            id = 0,
                            shoppingListId = 0,
                            name = "Ingredient 2",
                            amount = 2,
                            unit = MeasureUnit.KILOGRAM.displayName,
                            imageUrl = null
                        )
                    ),
                    current = true
                )
            ),
            onDeleteIngredient = {},
            onClickAddIngredient = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

