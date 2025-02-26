package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.ShoppingDestination
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.components.ShoppingScreen
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.vimos.ShoppingScreenViewModel

@Composable
fun NavComponent(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ShoppingDestination,
        modifier = modifier
    ) {
        composable<ShoppingDestination> {
            val shoppingScreenViewModel = koinInject<ShoppingScreenViewModel>()
            val shoppingScreenState =
                shoppingScreenViewModel.state.collectAsStateWithLifecycle().value

            ShoppingScreen(
                state = shoppingScreenState,
                onRecipeDetailedIdChanged = {
                    shoppingScreenViewModel.updateRecipeDetailedId(it)
                },
                onSelectedShoppingListIdChanged = {
                    shoppingScreenViewModel.updateSelectedShoppingListId(it)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

