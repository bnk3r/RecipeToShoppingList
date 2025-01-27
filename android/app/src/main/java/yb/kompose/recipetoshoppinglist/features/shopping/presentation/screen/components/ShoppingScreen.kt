package yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel.VerticalSwipeablePanel
import yb.kompose.recipetoshoppinglist.features.core.presentation.util.dpToPx
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipeScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipesScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingViewModel
import kotlin.math.roundToInt

@Composable
fun ShoppingScreen(
    shoppingViewModel: ShoppingViewModel,
    categoryViewModel: CategoryViewModel,
    recipeViewModel: RecipeViewModel,
    modifier: Modifier = Modifier
) {

    val shoppingLists = shoppingViewModel.shoppingLists.collectAsStateWithLifecycle()

    val configuration = LocalConfiguration.current
    val widthPx = configuration.screenWidthDp.dp.dpToPx().roundToInt()

    var recipeDetailsVisible by remember { mutableStateOf(false) }
    var recipeDetailedId by remember { mutableStateOf<Int?>(null) }

    fun showRecipeDetails(id: Int) {
        recipeDetailedId = id
        recipeDetailsVisible = true
    }

    Box(
        modifier = modifier
    ) {
        VerticalSwipeablePanel(
            modifier = Modifier.fillMaxSize(),
            contentBehind = {
                CurrentShoppingList(
                    modifier = Modifier.fillMaxSize()
                )
            },
            panelBody = {
                RecipesScreen(
                    categoryViewModel = categoryViewModel,
                    recipeViewModel = recipeViewModel,
                    showRecipeDetails = { showRecipeDetails(it) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 120.dp)
                )
            },
            behindColor = MaterialTheme.colorScheme.primary,
            panelColor = MaterialTheme.colorScheme.surface
        )
        AnimatedVisibility(
            visible = recipeDetailsVisible,
            enter = slideInHorizontally(initialOffsetX = { widthPx }),
            exit = slideOutHorizontally(targetOffsetX = { widthPx })
        ) {
            recipeDetailedId?.let { id ->
                RecipeScreen(
                    recipeId = id,
                    recipeViewModel = recipeViewModel,
                    addToShoppingList = { /* TODO */ },
                    onBackPressed = { recipeDetailsVisible = false },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }


}

@Composable
fun CurrentShoppingList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO UI
    }
}