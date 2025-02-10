package yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import yb.kompose.recipetoshoppinglist.features.core.domain.models.FlowState
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel.VerticalSwipeablePanel
import yb.kompose.recipetoshoppinglist.features.core.presentation.util.dpToPx
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipeScreen
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components.RecipesScreen
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.components.ShoppingListComponent
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.vimos.ShoppingViewModel
import kotlin.math.roundToInt

@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val widthPx = configuration.screenWidthDp.dp.dpToPx().roundToInt()

    val shoppingListsState = viewModel.shoppingListsState.collectAsStateWithLifecycle().value
    val shoppingLists = viewModel.shoppingLists.collectAsStateWithLifecycle().value
    val currentList = viewModel.currentShoppingList.collectAsStateWithLifecycle().value
    val ingredients = viewModel.ingredients.collectAsStateWithLifecycle()

    var recipeDetailsVisible by remember { mutableStateOf(false) }
    var recipeDetailedId by remember { mutableStateOf<Int?>(null) }

    fun showRecipeDetails(id: Int) {
        recipeDetailedId = id
        recipeDetailsVisible = true
    }

    LaunchedEffect(Unit) {
        viewModel.getShoppingLists()
    }

    Box(
        modifier = modifier
    ) {
        VerticalSwipeablePanel(
            modifier = Modifier.fillMaxSize(),
            contentBehind = {
                when (shoppingListsState) {
                    FlowState.LOADING -> {
                        LoadingShoppingLists(modifier = Modifier.fillMaxSize())
                    }

                    FlowState.SUCCESS -> {
                        when (currentList) {
                            null -> {
                                CreateNewShoppingListComponent(
                                    onClickCreate = {
                                        viewModel.addNewShoppingList(true)
                                    },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            else -> {
                                ShoppingListComponent(
                                    shoppingList = currentList,
                                    ingredients = ingredients.value,
                                    onAdd = { ingredient ->
                                        viewModel.addIngredientToCurrentList(ingredient)
                                    },
                                    onDelete = {
                                        viewModel.removeIngredientFromCurrentList(it)
                                    },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    FlowState.ERROR -> {
                        ErrorShoppingLists(modifier = Modifier.fillMaxSize())
                    }

                    else -> {}
                }
            },
            panelBody = {
                RecipesScreen(
                    showRecipeDetails = { showRecipeDetails(it) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 120.dp)
                )
            },
            behindColor = MaterialTheme.colorScheme.primary,
            panelColor = FloatingActionButtonDefaults.containerColor
        )
        AnimatedVisibility(
            visible = recipeDetailsVisible,
            enter = slideInHorizontally(initialOffsetX = { widthPx }),
            exit = slideOutHorizontally(targetOffsetX = { widthPx })
        ) {
            recipeDetailedId?.let { id ->
                RecipeScreen(
                    recipeId = id,
                    addToShoppingList = { /* TODO */ },
                    onBackPressed = { recipeDetailsVisible = false },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }


}

@Composable
fun ShoppingListLoadingComponent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun CreateNewShoppingListComponent(
    modifier: Modifier = Modifier,
    onClickCreate: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClickCreate,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text(
                text = "Create List"
            )
        }
    }
}

@Composable
fun LoadingShoppingLists(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorShoppingLists(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text("ERROR !")
    }
}