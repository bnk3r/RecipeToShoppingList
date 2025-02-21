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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text.SectionTitle
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.components.AddIngredientPanel
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.components.RecipeIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.panels.vimos.RecipePanelViewModel

@Composable
fun RecipePanel(
    viewModel: RecipePanelViewModel = koinViewModel(),
    recipeId: Long?,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val recipe = viewModel.recipe.collectAsStateWithLifecycle().value
    val recipeIsLoading = viewModel.recipeIsLoading.collectAsStateWithLifecycle().value

    var ingredientToAdd by remember { mutableStateOf<UiIngredient?>(null) }

    LaunchedEffect(recipeId) {
        recipeId?.let { id ->
            viewModel.getRecipe(id)
        }
    }

    Box(
        modifier = modifier
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            recipe?.let { r ->
                item {
                    CachedAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        url = r.imgUrl,
                        title = r.title
                    )
                }
                item {
                    Text(
                        text = r.title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp)
                    )
                }
                item {
                    val instructions = r.instructions ?: return@item
                    Text(
                        text = instructions,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 32.dp)
                    )

                }
                if (r.ingredients.isNotEmpty()) {
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
                            r.ingredients.forEachIndexed { i, ingredient ->
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    RecipeIngredient(
                                        ingredient = ingredient,
                                        addToShoppingList = {
                                            ingredientToAdd = it
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    if (i < r.ingredients.lastIndex) {
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

            if (recipeIsLoading) {
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
            visible = ingredientToAdd != null,
            ingredientToAdd = ingredientToAdd,
            onBackPressed = {
                ingredientToAdd = null
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    BackHandler {
        viewModel.clearRecipeJob()
        onBackPressed()
    }

}