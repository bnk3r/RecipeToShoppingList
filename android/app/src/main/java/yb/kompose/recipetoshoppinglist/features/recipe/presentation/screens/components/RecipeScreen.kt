package yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.recipes.vimos.RecipeViewModel

@Composable
fun RecipeScreen(
    recipeId: Int,
    recipeViewModel: RecipeViewModel,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    var recipe by remember { mutableStateOf<UiRecipe?>(null) }

    LaunchedEffect(recipeId) {
        coroutineScope.launch(Dispatchers.Default) {
            recipeViewModel.getRecipeDetailed(recipeId).collect { r ->
                recipe = r
            }
        }
    }

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            recipe?.let { r ->
                Text(
                    text = "Name = ${r.title}"
                )
            } ?: Text(
                text = "No Recipe"
            )
        }
    }

}