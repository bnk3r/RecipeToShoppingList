package yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.image.CachedAsyncImage
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.slide_panel.SlideEndPanel
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text.SectionTitle
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiIngredient
import yb.kompose.recipetoshoppinglist.features.recipe.presentation.ingredients.models.AddIngredientPanelState

@Composable
fun AddIngredientPanel(
    state: AddIngredientPanelState,
    visible: Boolean,
    ingredientToAdd: UiIngredient?,
    onRegisterIngredientToAdd: (UiIngredient) -> Unit,
    onRefIngredientChanged: (UiIngredient) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {

    LaunchedEffect(ingredientToAdd) {
        ingredientToAdd?.let { onRegisterIngredientToAdd(it) }
    }

    SlideEndPanel(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        visible = visible
    ) {
        state.ingredientToAdd?.let { ingredient ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 120.dp, bottom = 16.dp)
                    .padding(16.dp)
            ) {
                SectionTitle(stringResource(R.string.add_this_ingredient))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.name),
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )
                    Text(
                        text = ingredient.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .weight(2f)
                            .padding(16.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.quantity),
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )
                    Text(
                        text = ingredient.amount,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .weight(2f)
                            .padding(16.dp)
                    )
                }
                // Corresponding ingredients (by name)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .border(2.dp, MaterialTheme.colorScheme.onSurface),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    when (state.areRefIngredientsLoading) {
                        true -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        false -> {
                            state.refIngredients?.let { ingredients ->
                                items(ingredients) { refIngredient ->
                                    RefIngredient(
                                        modifier = Modifier.fillMaxWidth(),
                                        ingredient = refIngredient,
                                        selected = state.refIngredient == refIngredient,
                                        onClick = {
                                            onRefIngredientChanged(refIngredient)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        BackHandler {
            onBackPressed()
        }
    }
}

@Composable
fun RefIngredient(
    modifier: Modifier = Modifier,
    ingredient: UiIngredient,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .background(
                when (selected) {
                    true -> Color.Yellow
                    false -> Color.Transparent
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CachedAsyncImage(
            modifier = Modifier
                .width(36.dp)
                .aspectRatio(1f),
            url = ingredient.imgUrl,
            title = ingredient.name
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = ingredient.name
        )
    }
}