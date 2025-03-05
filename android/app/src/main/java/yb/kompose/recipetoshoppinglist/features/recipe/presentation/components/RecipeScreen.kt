package yb.kompose.recipetoshoppinglist.features.recipe.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text.SectionTitle
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.models.states.RecipeScreenState

@Composable
fun RecipeScreen(
    state: RecipeScreenState,
    onClickAddIngredientToShoppingList: (UiIngredient) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        state.recipe?.let { recipe ->
            item {
                SectionTitle(
                    title = recipe.title,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
            item {
                CachedAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .padding(bottom = 32.dp),
                    url = recipe.imgUrl,
                    title = recipe.title,
                    contentScale = ContentScale.Crop
                )
            }
            item {
                val instructions = recipe.instructions ?: return@item
                Text(
                    text = instructions,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
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
                            if (ingredient.name.isNotBlank() && ingredient.amount.isNotBlank()) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    RecipeIngredient(
                                        ingredient = ingredient,
                                        addToShoppingList = onClickAddIngredientToShoppingList,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(56.dp)
                                            .padding(horizontal = 16.dp, vertical = 4.dp)
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
}