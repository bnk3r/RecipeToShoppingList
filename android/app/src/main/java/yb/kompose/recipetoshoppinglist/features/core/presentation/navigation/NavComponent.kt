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
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.ProfileDestination
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.RecipesDestination
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.ShoppingListsDestination
import yb.kompose.recipetoshoppinglist.features.profile.presentation.screen.components.ProfileScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screen.components.RecipesScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screen.vimos.RecipesScreenViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.vimos.ShoppingListsDashboardViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.components.ShoppingListsScreen

@Composable
fun NavComponent(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                onClickShoppingLists = { navController.navigate(ShoppingListsDestination) },
                onClickRecipes = { navController.navigate(RecipesDestination) },
                onClickProfile = { navController.navigate(ProfileDestination) },
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
                val shoppingListsDashboardViewModel = koinInject<ShoppingListsDashboardViewModel>()
                val shoppingListsDashboardState =
                    shoppingListsDashboardViewModel.state.collectAsStateWithLifecycle().value
                ShoppingListsScreen(
                    state = shoppingListsDashboardState,
                    onClickAddNewList = {
                        shoppingListsDashboardViewModel.addNewListAsCurrent()
                    },
                    onSelectedShoppingListIdChanged = { /* TODO */ },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<RecipesDestination> {
                val recipesScreenViewModel = koinInject<RecipesScreenViewModel>()
                val recipesScreenState =
                    recipesScreenViewModel.state.collectAsStateWithLifecycle().value
                RecipesScreen(
                    state = recipesScreenState,
                    onRecipeSearchQueryChanged = {
                        recipesScreenViewModel.updateQueryRecipe(it)
                    },
                    onSelectedCategoryChanged = {
                        recipesScreenViewModel.updateSelectedCategory(it)
                    },
                    onClickRecipe = { /* TODO */ },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<ProfileDestination> {
                ProfileScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }


}

