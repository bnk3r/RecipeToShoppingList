package yb.kompose.recipetoshoppinglist.features.shopping.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.models.ui.SelectionIngredient

@Composable
fun ChooseIngredientItem(
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

@Preview(showBackground = true)
@Composable
private fun ChooseIngredientItemPreview() {
    ChooseIngredientItem(
        ingredient = SelectionIngredient(
            name = "Chicken",
            imageUrl = "www.themealdb.com/images/ingredients/Chicken.png"
        ),
        onClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}