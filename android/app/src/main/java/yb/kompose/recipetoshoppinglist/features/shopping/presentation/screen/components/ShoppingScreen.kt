package yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel.VerticalSwipeablePanel
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.slide_panel.SlideEndPanel
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.slide_panel.SlideStartPanel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.components.RecipePanel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.components.RecipesPanel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.components.ShoppingListsDashboard
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.components.ShoppingList

@Composable
fun ShoppingScreen(
    modifier: Modifier = Modifier
) {
    var recipeDetailedId by remember { mutableStateOf<Long?>(null) }
    var selectedShoppingListId by remember { mutableStateOf<Long?>(null) }

    Box(
        modifier = modifier
    ) {
        // CONTENT (visible by default): SHOPPING LISTS
        // SWIPEABLE PANEL (bottom) : RECIPES
        VerticalSwipeablePanel(
            modifier = Modifier.fillMaxSize(),
            contentBehind = {
                ShoppingListsDashboard(
                    modifier = Modifier.fillMaxSize(),
                    onListClicked = { id ->
                        selectedShoppingListId = id
                    }
                )
            },
            panelBody = {
                RecipesPanel(
                    showRecipeDetails = { id ->
                        recipeDetailedId = id
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 120.dp)
                )
            },
            behindColor = MaterialTheme.colorScheme.primary,
            panelColor = FloatingActionButtonDefaults.containerColor
        )

        // PANEL (slide from right) : RECIPE
        SlideEndPanel(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            visible = recipeDetailedId != null,
        ) {
            RecipePanel(
                recipeId = recipeDetailedId,
                addToShoppingList = { /* TODO */ },
                onBackPressed = { recipeDetailedId = null },
                modifier = modifier.fillMaxSize()
            )
        }

        // PANEL (slide from left) : SHOPPING LIST
        SlideStartPanel(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            visible = selectedShoppingListId != null
        ) {
            ShoppingList(
                modifier = Modifier.fillMaxSize(),
                shoppingListId = selectedShoppingListId,
                onBackPressed = { selectedShoppingListId = null },
            )
        }
    }
}

