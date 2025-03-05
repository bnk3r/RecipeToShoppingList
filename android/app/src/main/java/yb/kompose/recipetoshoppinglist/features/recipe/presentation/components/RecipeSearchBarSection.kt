package yb.kompose.recipetoshoppinglist.features.recipe.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.DesignRoundedSquareIconButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.search.DesignSearchBar
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.text.SectionTitle

@Composable
fun RecipeSearchBarSection(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    hideKeyboard: Boolean,
    onHideKeyboard: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        SectionTitle(
            title = stringResource(R.string.section_search_recipe_title),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DesignSearchBar(
                query = query,
                onQueryChange = onQueryChange,
                placeholder = stringResource(R.string.search_placeholder),
                onSearch = onSearch,
                hideKeyboard = hideKeyboard,
                onFocusClear = { onHideKeyboard(false) },
                modifier = Modifier
                    .height(56.dp)
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            DesignRoundedSquareIconButton(
                size = 56.dp,
                background = MaterialTheme.colorScheme.primary,
                iconColor = MaterialTheme.colorScheme.onPrimary,
                onClick = { onSearch(query) },
                imageVector = Icons.Default.Tune,
                contentDescription = stringResource(R.string.filters)
            )
        }
    }

}