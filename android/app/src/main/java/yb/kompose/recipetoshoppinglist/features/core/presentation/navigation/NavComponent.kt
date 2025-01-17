package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import yb.kompose.recipetoshoppinglist.features.core.domain.models.nav.RecipesDestination

@Composable
fun NavComponent(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RecipesDestination,
        modifier = modifier
    ) {
        composable<RecipesDestination> {

        }
    }
}