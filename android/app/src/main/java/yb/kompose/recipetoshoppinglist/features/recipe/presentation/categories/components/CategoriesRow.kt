package yb.kompose.recipetoshoppinglist.features.recipe.presentation.categories.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text.SectionTitle
import yb.kompose.recipetoshoppinglist.features.recipe.domain.models.UiCategory

@Composable
fun CategoriesRow(
    categories: List<UiCategory>,
    selectedCategory: UiCategory?,
    onCategorySelected: (UiCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        SectionTitle(
            title = stringResource(R.string.section_categories_title),
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                Spacer(modifier = Modifier.width(0.dp))
            }
            items(categories) { category ->
                SelectableCategory(
                    category = category,
                    selected = selectedCategory == category,
                    onClick = { onCategorySelected(category) }
                )
            }
            item {
                Spacer(modifier = Modifier.width(0.dp))
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun CategoriesRowPreview() {
    val beef = UiCategory(
        name = "Beef",
        imageUrl = ""
    )
    val chicken = UiCategory(
        name = "Chicken",
        imageUrl = ""
    )
    CategoriesRow(
        categories = listOf(beef, chicken),
        selectedCategory = beef,
        onCategorySelected = {},
        modifier = Modifier.fillMaxWidth()
    )
}