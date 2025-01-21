package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import yb.kompose.recipetoshoppinglist.features.core.domain.models.nav.RecipesDestination
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.RecipesScreen

@Composable
fun NavComponent(
    categoryViewModel: CategoryViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RecipesDestination,
        modifier = modifier
    ) {
        composable<RecipesDestination> {
            RecipesScreen(
                categoryViewModel = categoryViewModel
            )
        }
    }
}