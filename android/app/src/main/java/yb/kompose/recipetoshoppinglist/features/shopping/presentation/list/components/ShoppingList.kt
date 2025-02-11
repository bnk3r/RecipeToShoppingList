package yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components.AddShoppingItemDialog
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components.DeleteableIngredient
import java.time.format.DateTimeFormatter

@Composable
fun ShoppingList(
    shoppingList: UiShoppingList,
    ingredients: List<UiIngredient>?,
    onAdd: (UiShoppingListIngredient) -> Unit,
    onDelete: (UiShoppingListIngredient) -> Unit,
    modifier: Modifier = Modifier
) {

    var addIngredientDialogVisible by remember { mutableStateOf(false) }

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
                    text = shoppingList.updatedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
            items(shoppingList.ingredients) { ingredient ->
                DeleteableIngredient(
                    ingredient = ingredient,
                    delete = { onDelete(ingredient) },
                    modifier = Modifier.fillMaxWidth()
                )
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
                    onClick = {
                        addIngredientDialogVisible = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        }

        ingredients?.let {
            AnimatedVisibility(
                visible = addIngredientDialogVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AddShoppingItemDialog(
                    shoppingListId = shoppingList.id,
                    ingredients = it,
                    onDismissRequest = {
                        addIngredientDialogVisible = false
                    },
                    onConfirmation = { ingredient ->
                        onAdd(ingredient)
                        addIngredientDialogVisible = false
                    }
                )
            }
        }


    }

}

