package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.AddIngredientFromShoppingListDestination
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.ProfileDestination
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.RecipeDestination
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.RecipesDestination
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.ShoppingListDestination
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.ShoppingListsDestination
import yb.kompose.recipetoshoppinglist.features.profile.presentation.screen.components.ProfileScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.components.RecipeScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.components.RecipesScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeScreenViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipesScreenViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.components.AddIngredientFromShoppingListScreen
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.components.ShoppingListScreen
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.components.ShoppingListsScreen
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.AddIngredientFromShoppingListViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingListScreenViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingListsScreenViewModel

@Composable
fun NavComponent(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                onClickShoppingLists = {
                    navController.navigate(
                        ShoppingListsDestination,
                        navOptions { popUpTo<ShoppingListsDestination> { inclusive = true } }
                    )
                },
                onClickRecipes = {
                    navController.navigate(
                        RecipesDestination,
                        navOptions { popUpTo<RecipesDestination> { inclusive = true } }
                    )
                },
                onClickProfile = {
                    navController.navigate(
                        ProfileDestination,
                        navOptions { popUpTo<ProfileDestination> { inclusive = true } }
                    )
                },
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
                val shoppingListsScreenViewModel = koinInject<ShoppingListsScreenViewModel>()
                val shoppingListsScreenState =
                    shoppingListsScreenViewModel.state.collectAsStateWithLifecycle().value
                ShoppingListsScreen(
                    state = shoppingListsScreenState,
                    onClickAddNewList = {
                        shoppingListsScreenViewModel.addNewListAsCurrent()
                    },
                    onClickShoppingList = {
                        navController.navigate(ShoppingListDestination(id = it))
                    },
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
                    onClickRecipe = {
                        navController.navigate(RecipeDestination(id = it))
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<ProfileDestination> {
                ProfileScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<ShoppingListDestination> { backStackEntry ->
                val id = backStackEntry.toRoute<ShoppingListDestination>().id
                val shoppingListScreenViewModel = koinInject<ShoppingListScreenViewModel>()
                val shoppingListScreenState =
                    shoppingListScreenViewModel.state.collectAsStateWithLifecycle().value

                LaunchedEffect(id) {
                    shoppingListScreenViewModel.updateShoppingListId(id)
                }

                ShoppingListScreen(
                    state = shoppingListScreenState,
                    onClickAddIngredient = {
                        navController.navigate(AddIngredientFromShoppingListDestination(it))
                    },
                    onDeleteIngredient = {
                        shoppingListScreenViewModel.deleteIngredient(it)
                    }
                )
            }

            composable<RecipeDestination> { backStackEntry ->
                val id = backStackEntry.toRoute<RecipeDestination>().id
                val recipeScreenViewModel = koinInject<RecipeScreenViewModel>()
                val recipeScreenState =
                    recipeScreenViewModel.state.collectAsStateWithLifecycle().value

                LaunchedEffect(id) {
                    recipeScreenViewModel.updateRecipeId(id)
                }

                RecipeScreen(
                    state = recipeScreenState,
                    onIngredientToAddChanged = { /* TODO */ },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<AddIngredientFromShoppingListDestination> { backStackEntry ->
                val id =
                    backStackEntry.toRoute<AddIngredientFromShoppingListDestination>().shoppingListId
                val addIngredientFromShoppingListViewModel =
                    koinViewModel<AddIngredientFromShoppingListViewModel>()
                val addIngredientFromShoppingListState =
                    addIngredientFromShoppingListViewModel.state.collectAsStateWithLifecycle().value

                LaunchedEffect(id) {
                    addIngredientFromShoppingListViewModel.updateShoppingListId(id)
                }

                AddIngredientFromShoppingListScreen(
                    state = addIngredientFromShoppingListState,
                    onIngredientSelected = {
                        addIngredientFromShoppingListViewModel.updateIngredientToAdd(it)
                    },
                    onIngredientAmountChanged = {
                        addIngredientFromShoppingListViewModel.updateIngredientToAddAmount(it)
                    },
                    onIngredientUnitChanged = {
                        addIngredientFromShoppingListViewModel.updateIngredientToAddUnit(it)
                    },
                    onSubmitIngredient = {
                        addIngredientFromShoppingListViewModel.addIngredientToShoppingList()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(vertical = 56.dp),
                )
            }
        }
    }


}

