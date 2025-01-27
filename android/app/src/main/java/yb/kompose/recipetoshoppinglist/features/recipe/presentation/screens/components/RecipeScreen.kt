package yb.kompose.recipetoshoppinglist.features.recipe.presentation.screens.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.FrenchTranslatedText
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.FrenchTranslatedTitle
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.SectionTitle
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiRecipe
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.vimos.RecipeViewModel

@Composable
fun RecipeScreen(
    recipeId: Int,
    recipeViewModel: RecipeViewModel,
    addToShoppingList: (UiIngredient) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    var recipe by remember { mutableStateOf<UiRecipe?>(null) }

    val context = LocalContext.current

    LaunchedEffect(recipeId) {
        coroutineScope.launch(Dispatchers.Default) {
            recipeViewModel.getRecipeDetailed(recipeId).collect { recipe = it }
        }
    }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        recipe?.let { r ->
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(r.imgUrl)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = r.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )
            }
            item {
                FrenchTranslatedTitle(
                    title = r.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                )
            }
            item {
                val instructions = r.instructions ?: return@item
                FrenchTranslatedText(
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
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    FrenchTranslatedText(
                                        text = "${ingredient.name} - ${ingredient.amount}"
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ShoppingBasket,
                                        contentDescription = stringResource(R.string.add_to_shopping_list),
                                        modifier = Modifier.clickable {
                                            addToShoppingList(ingredient)
                                        }
                                    )
                                }
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
        } ?: item {
            Text(
                text = "No Recipe found with id $recipeId"
            )
        }
    }

    BackHandler {
        onBackPressed()
    }

}