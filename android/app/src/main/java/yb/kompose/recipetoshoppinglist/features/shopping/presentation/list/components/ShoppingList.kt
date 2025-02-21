package yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.slide_panel.SlideEndPanel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components.AddShoppingIngredientPanel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components.DeleteableIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.vimos.ShoppingListViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ShoppingList(
    viewModel: ShoppingListViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    shoppingListId: Long?,
    onBackPressed: () -> Unit
) {

    val shoppingList = viewModel.shoppingList.collectAsStateWithLifecycle().value
    val shoppingListIsLoading = viewModel.shoppingListIsLoading.collectAsStateWithLifecycle().value

    var addIngredientPanelVisible by remember { mutableStateOf(false) }

    LaunchedEffect(shoppingListId) {
        shoppingListId?.let { id ->
            viewModel.getShoppingList(id)
        }
    }

    shoppingList?.let { list ->
        Box(
            modifier = modifier
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 120.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = list.updatedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }
                items(list.ingredients) { ingredient ->
                    DeleteableIngredient(
                        ingredient = ingredient,
                        delete = { viewModel.deleteIngredient(ingredient) },
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
                            addIngredientPanelVisible = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            }

            // LOADING SHOPPING LIST INDICATOR
            if (shoppingListIsLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            SlideEndPanel(
                modifier = Modifier.fillMaxSize(),
                visible = addIngredientPanelVisible
            ) {
                AddShoppingIngredientPanel(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(vertical = 56.dp),
                    shoppingListId = list.id,
                    onBackPressed = { addIngredientPanelVisible = false }
                )
            }

        }
    }

    BackHandler {
        onBackPressed()
    }

}

