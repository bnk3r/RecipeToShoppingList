package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import yb.kompose.recipetoshoppinglist.features.core.domain.models.nav.ShoppingDestination
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.components.ShoppingScreen
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingViewModel

@Composable
fun NavComponent(
    categoryViewModel: CategoryViewModel,
    recipeViewModel: RecipeViewModel,
    shoppingViewModel: ShoppingViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ShoppingDestination,
        modifier = modifier
    ) {
        composable<ShoppingDestination> {
            ShoppingScreen(
                shoppingViewModel = shoppingViewModel,
                categoryViewModel = categoryViewModel,
                recipeViewModel = recipeViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}