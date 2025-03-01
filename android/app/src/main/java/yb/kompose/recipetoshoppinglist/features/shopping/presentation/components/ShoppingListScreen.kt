package yb.kompose.recipetoshoppinglist.features.shopping.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states.ShoppingListScreenState
import java.time.format.DateTimeFormatter

@Composable
fun ShoppingListScreen(
    state: ShoppingListScreenState,
    onClickAddIngredient: (Long) -> Unit,
    onDeleteIngredient: (UiShoppingListIngredient) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        when {
            state.isShoppingListLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            else -> {
                state.shoppingList?.let { shoppingList ->
                    Box(
                        modifier = modifier
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                Text(
                                    text = shoppingList.updatedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                    modifier = Modifier.padding(bottom = 32.dp)
                                )
                            }
                            items(shoppingList.ingredients) { ingredient ->
                                DeleteableIngredient(
                                    ingredient = ingredient,
                                    onDelete = { onDeleteIngredient(ingredient) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                FloatingActionButton(
                                    onClick = {
                                        onClickAddIngredient(shoppingList.id)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, backgroundColor = 0xffffffff)
@Composable
private fun ShoppingListPreview() {
    ShoppingListScreen(
        state = ShoppingListScreenState(),
        onDeleteIngredient = {},
        onClickAddIngredient = {},
        modifier = Modifier.fillMaxSize()
    )
}

