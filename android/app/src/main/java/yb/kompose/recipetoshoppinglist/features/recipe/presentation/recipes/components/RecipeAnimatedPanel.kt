package yb.kompose.recipetoshoppinglist.features.recipe.presentation.recipes.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.core.presentation.util.dpToPx
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipeScreen
import kotlin.math.roundToInt

@Composable
fun RecipeAnimatedPanel(
    modifier: Modifier = Modifier,
    recipeId: Long,
    visible: Boolean,
    onBackPressed: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val widthPx = configuration.screenWidthDp.dp.dpToPx().roundToInt()

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(initialOffsetX = { widthPx }),
        exit = slideOutHorizontally(targetOffsetX = { widthPx })
    ) {
        RecipeScreen(
            recipeId = recipeId,
            addToShoppingList = { /* TODO */ },
            onBackPressed = onBackPressed,
            modifier = modifier
        )
    }
}