package yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.slide_panel.SlideEndPanel
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.slide_panel.SlideStartPanel
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.swipe_panel.VerticalSwipeablePanel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.components.RecipePanel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.components.RecipesPanel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos.RecipePanelViewModel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos.RecipesPanelViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.components.ShoppingListsDashboard
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.dashboard.vimos.ShoppingListsDashboardViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.components.ShoppingList
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.list.vimos.ShoppingListViewModel
import yb.kompose.recipetoshoppinglist.features.shopping.presentation.screen.models.ShoppingScreenState

@Composable
fun ShoppingScreen(
    state: ShoppingScreenState,
    onSelectedShoppingListIdChanged: (Long?) -> Unit,
    onRecipeDetailedIdChanged: (Long?) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        // CONTENT (visible by default): SHOPPING LISTS
        // SWIPEABLE PANEL (bottom) : RECIPES
        VerticalSwipeablePanel(
            modifier = Modifier.fillMaxSize(),
            contentBehind = {
                val shoppingListsDashboardViewModel = koinInject<ShoppingListsDashboardViewModel>()
                val shoppingListsDashboardState =
                    shoppingListsDashboardViewModel.state.collectAsStateWithLifecycle().value
                ShoppingListsDashboard(
                    state = shoppingListsDashboardState,
                    onCreateNewList = {
                        shoppingListsDashboardViewModel.addNewListAsCurrent()
                    },
                    onClickShoppingList = onSelectedShoppingListIdChanged,
                    modifier = Modifier.fillMaxSize()
                )
            },
            panelBody = {
                val recipesPanelViewModel = koinInject<RecipesPanelViewModel>()
                val recipesPanelState =
                    recipesPanelViewModel.state.collectAsStateWithLifecycle().value
                RecipesPanel(
                    state = recipesPanelState,
                    onRecipeSearchQueryChanged = {
                        recipesPanelViewModel.updateQueryRecipe(it)
                    },
                    onSelectedCategoryChanged = {
                        recipesPanelViewModel.updateSelectedCategory(it)
                    },
                    onClickRecipe = onRecipeDetailedIdChanged,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 120.dp)
                )
            },
            behindColor = MaterialTheme.colorScheme.primary,
            panelColor = FloatingActionButtonDefaults.containerColor
        )

        // PANEL (slide from right) : RECIPE
        SlideEndPanel(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            visible = state.isRecipePanelVisible,
        ) {
            val recipePanelViewModel = koinInject<RecipePanelViewModel>()
            val recipePanelState =
                recipePanelViewModel.state.collectAsStateWithLifecycle().value

            LaunchedEffect(state.recipeDetailedId) {
                if (state.recipeDetailedId == null || state.recipeDetailedId == -1L) {
                    return@LaunchedEffect
                }
                recipePanelViewModel.updateRecipeId(state.recipeDetailedId)
            }

            RecipePanel(
                state = recipePanelState,
                onIngredientToAddChanged = {
                    recipePanelViewModel.updateIngredientToAdd(it)
                },
                onBackPressed = {
                    recipePanelViewModel.clear()
                    onRecipeDetailedIdChanged(null)
                },
                modifier = modifier.fillMaxSize()
            )
        }

        // PANEL (slide from left) : SHOPPING LIST
        SlideStartPanel(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            visible = state.isShoppingListPanelVisible
        ) {
            val shoppingListViewModel = koinInject<ShoppingListViewModel>()
            val shoppingListState =
                shoppingListViewModel.state.collectAsStateWithLifecycle().value

            LaunchedEffect(state.selectedShoppingListId) {
                val id = state.selectedShoppingListId
                if (id == null || id == -1L) {
                    return@LaunchedEffect
                }
                shoppingListViewModel.updateShoppingListId(id)
            }

            ShoppingList(
                state = shoppingListState,
                onDeleteIngredient = {
                    shoppingListViewModel.deleteIngredient(it)
                },
                onAddIngredientPanelVisibilityChanged = {
                    shoppingListViewModel.updateAddIngredientPanelVisibility(it)
                },
                onBackPressed = {
                    onSelectedShoppingListIdChanged(null)
                },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(showSystemUi = true, backgroundColor = 0xffffffff)
@Composable
private fun ShoppingScreenPreview() {
    ShoppingScreen(
        state = ShoppingScreenState(),
        onRecipeDetailedIdChanged = {},
        onSelectedShoppingListIdChanged = {},
        modifier = Modifier.fillMaxSize()
    )
}

