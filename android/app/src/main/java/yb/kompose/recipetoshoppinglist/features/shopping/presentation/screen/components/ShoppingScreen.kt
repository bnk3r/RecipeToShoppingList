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
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.recipes.components.RecipeAnimatedPanel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipesPanel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.components.ShoppingListsDashboard

@Composable
fun ShoppingScreen(
    modifier: Modifier = Modifier
) {
    var recipeDetailsVisible by remember { mutableStateOf(false) }
    var recipeDetailedId by remember { mutableStateOf<Long?>(null) }

    Box(
        modifier = modifier
    ) {
        // CONTENT (visible by default): SHOPPING LISTS
        // SWIPEABLE PANEL (bottom) : RECIPES
        VerticalSwipeablePanel(
            modifier = Modifier.fillMaxSize(),
            contentBehind = {
                ShoppingListsDashboard(
                    modifier = Modifier.fillMaxSize()
                )
            },
            panelBody = {
                RecipesPanel(
                    showRecipeDetails = { id ->
                        recipeDetailedId = id
                        recipeDetailsVisible = true
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
        // Visible when an ID is set
        recipeDetailedId?.let { id ->
            RecipeAnimatedPanel(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                recipeId = id,
                visible = recipeDetailsVisible,
                onBackPressed = { recipeDetailsVisible = false }
            )
        }

    }
}

