package yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import java.time.format.DateTimeFormatter

@Composable
fun ShoppingListComponent(
    shoppingList: UiShoppingList,
    ingredients: List<UiShoppingListIngredient>?,
    onDelete: (UiShoppingListIngredient) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = shoppingList.updatedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
            ingredients?.let { ing ->
                items(ing) { ingredient ->
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInHorizontally() + fadeIn(),
                        exit = slideOutHorizontally() + fadeOut()
                    ) {
                        ShoppingListItemComponent(
                            ingredient = ingredient,
                            delete = { onDelete(ingredient) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {}
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

