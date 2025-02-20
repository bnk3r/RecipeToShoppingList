package yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.vimos.ShoppingListsDashboardViewModel

@Composable
fun ShoppingListsDashboard(
    viewModel: ShoppingListsDashboardViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    onListClicked: (id: Long) -> Unit
) {
    val shoppingLists = viewModel.shoppingLists.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.getShoppingLists()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        item(
            span = { GridItemSpan(2) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
        item(
            span = { GridItemSpan(2) }
        ) {
            ShoppingListsDashboardAddItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = {
                    viewModel.addNewListAsCurrent()
                }
            )
        }
        items(shoppingLists) { list ->
            ShoppingListDashboardItem(
                modifier = Modifier.aspectRatio(1f),
                shoppingList = list,
                onClick = { onListClicked(list.id) }
            )
        }
        item(
            span = { GridItemSpan(2) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
        }
    }

}