package yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.models.ShoppingListsDashboardState
import java.time.LocalDate

@Composable
fun ShoppingListsDashboard(
    state: ShoppingListsDashboardState,
    onCreateNewList: () -> Unit,
    onClickShoppingList: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
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
            ShoppingListsDashboardAddItemButton(
                onClick = onCreateNewList,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
        items(state.shoppingLists) { list ->
            ShoppingListDashboardItem(
                modifier = Modifier.aspectRatio(1f),
                shoppingList = list,
                onClick = { onClickShoppingList(list.id) }
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

@Preview(showBackground = true)
@Composable
private fun ShoppingListsDashboardPreview() {
    ShoppingListsDashboard(
        state = ShoppingListsDashboardState(
            shoppingLists = listOf(
                UiShoppingList(
                    id = 0,
                    updatedDate = LocalDate.now(),
                    ingredients = emptyList(),
                    current = true
                ),
                UiShoppingList(
                    id = 1,
                    updatedDate = LocalDate.now(),
                    ingredients = emptyList(),
                    current = false
                )
            )
        ),
        onCreateNewList = {},
        onClickShoppingList = {},
        modifier = Modifier.fillMaxSize()
    )
}