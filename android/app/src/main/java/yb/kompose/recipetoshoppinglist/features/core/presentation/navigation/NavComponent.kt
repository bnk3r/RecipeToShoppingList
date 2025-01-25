package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import yb.kompose.recipetoshoppinglist.features.core.domain.models.nav.RecipeDestination
import yb.kompose.recipetoshoppinglist.features.core.domain.models.nav.RecipesDestination
import yb.kompose.recipetoshoppinglist.features.core.domain.models.nav.ShoppingDestination
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipeScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipesScreen
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
        composable<RecipesDestination> {
            RecipesScreen(
                categoryViewModel = categoryViewModel,
                recipeViewModel = recipeViewModel,
                showRecipeDetails = { id ->
                    navController.navigate(RecipeDestination(id))
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<RecipeDestination> {
            val args = it.toRoute<RecipeDestination>()
            RecipeScreen(
                recipeId = args.id,
                recipeViewModel = recipeViewModel,
                addToShoppingList = { ingredient ->
                    // TODO shoppingListViewModel and consort -> edit or create current shopping list (DB)
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<ShoppingDestination> {
            ShoppingScreen(
                shoppingViewModel = shoppingViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}