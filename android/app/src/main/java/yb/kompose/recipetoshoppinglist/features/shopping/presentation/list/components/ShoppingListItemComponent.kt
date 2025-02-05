package yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel.HorizontalSwipeablePanel
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.Red
import yb.kompose.recipetoshoppinglist.features.shopping.domain.models.UiShoppingListIngredient
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components.ShoppingItemPanelBackContent
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.ingredient.components.ShoppingItemPanelContent

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun ShoppingListItemComponent(
    ingredient: UiShoppingListIngredient,
    delete: () -> Unit,
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
            ShoppingItemPanelContent(
                ingredient = ingredient,
                modifier = panelContentModifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            )
        },
        backContent = { swipeableState ->
            ShoppingItemPanelBackContent(
                onClickDelete = {
                    coroutineScope.launch {
                        swipeableState.animateTo(0)
                        delete()
                    }
                },
                modifier = panelContentModifier
                    .fillMaxSize()
                    .background(Red)
            )
        }
    )

}