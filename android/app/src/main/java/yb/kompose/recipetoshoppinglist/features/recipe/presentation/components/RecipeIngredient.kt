package yb.kompose.recipetoshoppinglist.features.recipe.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.DesignIconButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.models.DesignIconButtonStyle
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient

@Composable
fun RecipeIngredient(
    ingredient: UiIngredient,
    addToShoppingList: (ingredient: UiIngredient) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 4.dp)
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${ingredient.name} - ${ingredient.amount}",
                modifier = Modifier.weight(1f)
            )
            DesignIconButton(
                onClick = { addToShoppingList(ingredient) },
                imageVector = Icons.Default.ShoppingBasket,
                contentDescription = stringResource(R.string.add_to_shopping_list),
                style = DesignIconButtonStyle.PRIMARY
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeIngredientPreview() {
    RecipeIngredient(
        ingredient = UiIngredient(
            name = "Chicken",
            amount = "100g",
            imgUrl = "www.themealdb.com/images/ingredients/Chicken.png",
            thumbnailUrl = "www.themealdb.com/images/ingredients/Chicken-Small.png"
        ),
        addToShoppingList = {},
        modifier = Modifier.fillMaxWidth()
    )
}