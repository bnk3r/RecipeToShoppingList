package yb.kompose.recipetoshoppinglist.features.shopping.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text.SectionTitle
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import java.time.format.DateTimeFormatter

@Composable
fun ShoppingListItem(
    shoppingList: UiShoppingList,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        border = when (shoppingList.current) {
            true -> BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            false -> null
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SectionTitle(
                    title = stringResource(R.string.update)
                )
                SectionTitle(
                    title = shoppingList.updatedDate.format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    )
                )
                Text(
                    text = stringResource(
                        R.string.ingredients_count,
                        shoppingList.ingredients.size
                    )
                )
            }
        }
    }
}