package yb.kompose.recipetoshoppinglist.features.shopping.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.states.AddIngredientFromRecipeState

@Composable
fun AddIngredientFromRecipeScreen(
    state: AddIngredientFromRecipeState,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {

        item {
            // TODO : Title
        }

        item {
            // TODO : Ingredient passed reference
        }

        item {
            // TODO : Title select ingredient from DB reference
        }

        item {
            // TODO : Section Quantity / Unit
        }

        item {
            // TODO : Button submit (if valid)
        }

    }
}