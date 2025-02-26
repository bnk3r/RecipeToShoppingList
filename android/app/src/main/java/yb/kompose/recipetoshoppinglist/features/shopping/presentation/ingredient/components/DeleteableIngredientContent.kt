package yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.RoundedIconButton
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient

@Composable
fun DeleteableIngredientContent(
    ingredient: UiShoppingListIngredient,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(ingredient.imageUrl)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = ingredient.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${ingredient.amount}${ingredient.unit}"
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                RoundedIconButton(
                    icon = Icons.Default.Remove,
                    contentDescription = "",
                    onClick = {},
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                )
                RoundedIconButton(
                    icon = Icons.Default.Add,
                    contentDescription = "",
                    onClick = {},
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DeleteableIngredientContentPreview() {
    DeleteableIngredientContent(
        ingredient = UiShoppingListIngredient(
            id = 0,
            shoppingListId = 0,
            name = "Chicken",
            amount = 100,
            unit = MeasureUnit.GRAM.displayName,
            imageUrl = "www.themealdb.com/images/ingredients/Chicken.png"
        ),
        modifier = Modifier.fillMaxWidth()
    )
}