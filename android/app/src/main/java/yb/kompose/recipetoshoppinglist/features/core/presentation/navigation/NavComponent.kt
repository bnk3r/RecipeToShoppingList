package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.ShoppingListsDestination
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.components.ShoppingScreen
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.vimos.ShoppingScreenViewModel

@Composable
fun NavComponent(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                onClickShoppingLists = {},
                onClickRecipes = {},
                onClickProfile = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = ShoppingListsDestination,
            modifier = modifier.padding(innerPadding)
        ) {
            composable<ShoppingListsDestination> {
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


}

