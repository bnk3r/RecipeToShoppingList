package yb.kompose.recipetoshoppinglist.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.NavComponent
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.RecipeToShoppingListTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeToShoppingListTheme {
                NavComponent(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

}