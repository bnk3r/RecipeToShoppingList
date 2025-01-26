package yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel.VerticalSwipeablePanel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipesScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingViewModel

@Composable
fun ShoppingScreen(
    shoppingViewModel: ShoppingViewModel,
    categoryViewModel: CategoryViewModel,
    recipeViewModel: RecipeViewModel,
    showRecipeDetails: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val shoppingLists = shoppingViewModel.shoppingLists.collectAsStateWithLifecycle()

    VerticalSwipeablePanel(
        modifier = modifier,
        collapsePanelHeight = 120.dp,
        panelColor = MaterialTheme.colorScheme.primary,
        contentBehind = {
            CurrentShoppingList()
        },
        panelBody = {
            RecipesScreen(
                categoryViewModel = categoryViewModel,
                recipeViewModel = recipeViewModel,
                showRecipeDetails = showRecipeDetails,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 120.dp)
            )
        }
    )

}

@Composable
fun CurrentShoppingList(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(50) {
            Text("Hello")
        }
    }
}