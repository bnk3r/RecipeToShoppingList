package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.ShoppingDestination
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.components.ShoppingScreen
import yb.kompose.recipetoshoppinglist.features.test.presentation.screen.components.TestDesign
import yb.kompose.recipetoshoppinglist.features.test.presentation.screen.models.TestDesignDestination

@Composable
fun NavComponent(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TestDesignDestination,
        modifier = modifier
    ) {
        composable<ShoppingDestination> {
            ShoppingScreen(
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<TestDesignDestination> {
            TestDesign()
        }
    }
}

