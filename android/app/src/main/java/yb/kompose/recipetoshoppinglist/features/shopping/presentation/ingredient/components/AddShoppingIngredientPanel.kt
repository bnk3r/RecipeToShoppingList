package yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.picker.LongDropDownMenu
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text.SectionTitle
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.models.SelectionIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.vimos.AddIngredientViewModel

@Composable
fun AddShoppingIngredientPanel(
    viewModel: AddIngredientViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    shoppingListId: Long,
    onBackPressed: () -> Unit
) {

    val ingredients = viewModel.ing.collectAsStateWithLifecycle().value
    val ingredientsAreLoading = viewModel.ingLoading.collectAsStateWithLifecycle().value
    val unitsStr = viewModel.unitsStr.collectAsStateWithLifecycle().value
    val ingredientToAdd = viewModel.ingredientToAdd.collectAsStateWithLifecycle().value

    LaunchedEffect(shoppingListId) {
        viewModel.updateIngredient(shoppingListId = shoppingListId)
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        SectionTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            title = stringResource(R.string.add_ingredient_title)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(
                R.string.selected_ingredient,
                ingredientToAdd.selectedIngredient?.name ?: stringResource(R.string.nothing)
            )
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(2.dp, MaterialTheme.colorScheme.onSurface),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            when (ingredientsAreLoading) {
                true -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                false -> {
                    items(ingredients) { ingredient ->
                        ShoppingIngredient(
                            modifier = Modifier.fillMaxWidth(),
                            ingredient = ingredient,
                            onClick = {
                                viewModel.updateIngredient(
                                    selectedIngredient = ingredient
                                )
                            }
                        )
                    }
                }
            }
        }
        SectionTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 16.dp),
            title = stringResource(R.string.quantity)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = ingredientToAdd.amount.toString(),
                onValueChange = { viewModel.updateIngredient(amount = viewModel.amountToString(it)) },
                suffix = {
                    Text(
                        text = ingredientToAdd.unit
                    )
                }
            )
            LongDropDownMenu(
                menuItemData = unitsStr,
                onItemClick = { unit ->
                    viewModel.updateIngredient(unit = unit)
                }
            )
        }

        Button(
            onClick = {
                if (viewModel.isIngredientValid()) {
                    viewModel.addIngredientToShoppingList()
                    onBackPressed()
                }
            }
        ) {
            Text(
                text = stringResource(R.string.add_to_shopping_list)
            )
        }
    }

    BackHandler {
        onBackPressed()
    }

}

@Composable
fun ShoppingIngredient(
    modifier: Modifier = Modifier,
    ingredient: SelectionIngredient,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CachedAsyncImage(
            modifier = Modifier
                .width(36.dp)
                .aspectRatio(1f),
            url = ingredient.imageUrl,
            title = ingredient.name
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = ingredient.name
        )
    }
}