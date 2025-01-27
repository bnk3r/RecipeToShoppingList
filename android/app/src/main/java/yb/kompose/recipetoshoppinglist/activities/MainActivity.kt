package yb.kompose.recipetoshoppinglist.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import org.koin.android.ext.android.inject
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.NavComponent
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.RecipeToShoppingListTheme
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.vimos.CategoryViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingViewModel

class MainActivity : ComponentActivity() {

    private val categoryViewModel: CategoryViewModel by inject()
    private val recipeViewModel: RecipeViewModel by inject()
    private val shoppingViewModel: ShoppingViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeToShoppingListTheme {
                NavComponent(
                    categoryViewModel = categoryViewModel,
                    recipeViewModel = recipeViewModel,
                    shoppingViewModel = shoppingViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}