package yb.kompose.recipetoshoppinglist.features.shopping.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel.HorizontalSwipeablePanel
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.RedLight
import yb.kompose.recipetoshoppinglist.features.shopping.data.db.models.MeasureUnit
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun DeleteableIngredient(
    ingredient: UiShoppingListIngredient,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val itemHeight = 120.dp

    val panelContentModifier = Modifier
        .fillMaxWidth()
        .height(itemHeight)

    HorizontalSwipeablePanel(
        modifier = modifier,
        frontContent = {
            DeleteableIngredientContent(
                ingredient = ingredient,
                modifier = panelContentModifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            )
        },
        backContent = { swipeableState ->
            DeleteableIngredientBackground(
                onClickDelete = {
                    coroutineScope.launch {
                        swipeableState.animateTo(0)
                        onDelete()
                    }
                },
                modifier = panelContentModifier
                    .fillMaxSize()
                    .background(RedLight)
            )
        }
    )

}

@Preview
@Composable
private fun DeleteableIngredientPreview() {
    DeleteableIngredient(
        ingredient = UiShoppingListIngredient(
            id = 0,
            shoppingListId = 0,
            name = "Chicken",
            amount = 100,
            unit = MeasureUnit.GRAM,
            imageUrl = "www.themealdb.com/images/ingredients/Chicken.png"
        ),
        onDelete = {},
    )
}