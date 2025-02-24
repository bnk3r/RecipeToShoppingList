package yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text.SectionTitle
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.components.AddIngredientPanel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.components.RecipeIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.models.RecipePanelState

@Composable
fun RecipePanel(
    state: RecipePanelState,
    onIngredientToAddChanged: (UiIngredient?) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            state.recipe?.let { recipe ->
                item {
                    CachedAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        url = recipe.imgUrl,
                        title = recipe.title
                    )
                }
                item {
                    Text(
                        text = recipe.title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp)
                    )
                }
                item {
                    val instructions = recipe.instructions ?: return@item
                    Text(
                        text = instructions,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 32.dp)
                    )

                }
                if (recipe.ingredients.isNotEmpty()) {
                    item {
                        SectionTitle(
                            title = stringResource(R.string.ingredients),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            recipe.ingredients.forEachIndexed { i, ingredient ->
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    RecipeIngredient(
                                        ingredient = ingredient,
                                        addToShoppingList = onIngredientToAddChanged,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    if (i < recipe.ingredients.lastIndex) {
                                        HorizontalDivider(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }

            if (state.isRecipeLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

        }

        AddIngredientPanel(
            visible = state.isAddIngredientPanelVisible,
            ingredientToAdd = state.ingredientToAdd,
            onBackPressed = { onIngredientToAddChanged(null) },
            modifier = Modifier.fillMaxSize()
        )
    }

    BackHandler {
        onBackPressed()
    }

}

@Preview(showBackground = true)
@Composable
private fun RecipePanelPreview() {
    RecipePanel(
        state = RecipePanelState(
            recipeId = 52781,
            isRecipeLoading = false,
            getRecipeJob = null,
            ingredientToAdd = null,
            isAddIngredientPanelVisible = false,
            recipe = UiRecipe(
                id = 52781,
                title = "Irish stew",
                instructions = "Heat the oven to 180C/350F/gas mark 4. Drain and rinse the soaked wheat, put it in a medium pan with lots of water, bring to a boil and simmer for an hour, until cooked. Drain and set aside.\\r\\n\\r\\nSeason the lamb with a teaspoon of salt and some black pepper. Put one tablespoon of oil in a large, deep sauté pan for which you have a lid; place on a medium-high heat. Add some of the lamb – don't overcrowd the pan – and sear for four minutes on all sides. Transfer to a bowl, and repeat with the remaining lamb, adding oil as needed.\\r\\n\\r\\nLower the heat to medium and add a tablespoon of oil to the pan.",
                ingredients = listOf(
                    UiIngredient(
                        name = "whole wheat",
                        amount = "300g soaked overnight in water",
                        imgUrl = "www.themealdb.com/images/ingredients/whole wheat.png",
                        thumbnailUrl = "www.themealdb.com/images/ingredients/whole wheat-Small.png"
                    )
                ),
                imgUrl = "https://www.themealdb.com/images/media/meals/sxxpst1468569714.jpg",
                thumbnailUrl = "https://www.themealdb.com/images/media/meals/sxxpst1468569714.jpg",
                recipeUrl = null,
                category = "Beef",
                area = "Irish"
            )
        ),
        onBackPressed = {},
        onIngredientToAddChanged = {},
        modifier = Modifier.fillMaxSize()
    )
}