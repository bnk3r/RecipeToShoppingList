package yb.kompose.recipetoshoppinglist.features.core.presentation.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.OrangeDark
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.OrangeLight

@Composable
fun DesignSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (query: String) -> Unit,
    placeholder: String,
    onSearch: (query: String) -> Unit,
    hideKeyboard: Boolean = false,
    onFocusClear: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var isHintDisplayed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.onFocusChanged {
                isHintDisplayed = !it.hasFocus
            },
            value = query,
            onValueChange = {
                onQueryChange(it)
                onSearch(it)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    onSearch(query)
                }
            ),
            maxLines = 1,
            singleLine = true,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = modifier.size(28.dp)
                    )
                }
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.onSurface,
                selectionColors = TextSelectionColors(
                    handleColor = OrangeDark,
                    backgroundColor = OrangeLight
                ),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.tertiary
            )
        )
    }
    if (hideKeyboard) {
        focusManager.clearFocus()
        // Call onFocusClear to reset hideKeyboard state to false
        onFocusClear()
    }
}