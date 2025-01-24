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
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipeScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipesScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel

@Composable
fun NavComponent(
    categoryViewModel: CategoryViewModel,
    recipeViewModel: RecipeViewModel,
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
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}