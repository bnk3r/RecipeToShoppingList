package yb.kompose.recipetoshoppinglist.features.shopping.presentation.components

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
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states.ShoppingListsScreenState

@Composable
fun ShoppingListsScreen(
    state: ShoppingListsScreenState,
    onClickShoppingList: (Long) -> Unit,
    onClickAddNewList: () -> Unit,
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
            AddShoppingListButton(
                onClick = onClickAddNewList,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
        items(state.shoppingLists) { list ->
            ShoppingListItem(
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

@Preview(showSystemUi = true, backgroundColor = 0xffffffff)
@Composable
private fun ShoppingScreenPreview() {
    ShoppingListsScreen(
        state = ShoppingListsScreenState(),
        onClickAddNewList = {},
        onClickShoppingList = {},
        modifier = Modifier.fillMaxSize()
    )
}

