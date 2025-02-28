package yb.kompose.recipetoshoppinglist.features.shopping.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList

@Composable
fun ShoppingListItem(
    modifier: Modifier = Modifier,
    shoppingList: UiShoppingList,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        border = when (shoppingList.current) {
            true -> BorderStroke(4.dp, Color.Red)
            false -> null
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = shoppingList.id.toString()
            )
        }
    }
}