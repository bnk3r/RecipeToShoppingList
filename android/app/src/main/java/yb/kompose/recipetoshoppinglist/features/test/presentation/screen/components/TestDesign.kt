package yb.kompose.recipetoshoppinglist.features.test.presentation.screen.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditAttributes
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.DesignIconButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.DesignIconButtonStyle
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.OrangeDark
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.OrangeLight
import yb.kompose.recipetoshoppinglist.features.test.presentation.buttons.components.TestDesignButtons

@Composable
fun TestDesign() {

    val interactionSource = remember { MutableInteractionSource() }
    var hideKeyboard by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff2B2D30))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { hideKeyboard = true },
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DesignProfileAndSearchHeader(
                user = User("Clark"),
                hideKeyboard = hideKeyboard,
                onHideKeyboard = { hideKeyboard = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            TestDesignButtons(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
    }
}

data class User(
    val name: String
)

@Composable
fun DesignProfileAndSearchHeader(
    modifier: Modifier = Modifier,
    user: User,
    hideKeyboard: Boolean,
    onHideKeyboard: (hide: Boolean) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
            .padding(top = 56.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DesignRoundedImage(
                    bitmap = null,
                    contentDescription = "Avatar",
                    modifier = Modifier.size(38.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Hello, ${user.name}!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Check Amazing Recipes...",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            DesignIconButton(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notification",
                style = DesignIconButtonStyle.WHITE,
                onClick = {}
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DesignSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = stringResource(R.string.search_placeholder),
                hideKeyboard = hideKeyboard,
                onFocusClear = { onHideKeyboard(false) },
                onSearch = {}
            )
            DesignRoundedSquareIconButton(
                onClick = {},
                imageVector = Icons.Default.Tune,
                contentDescription = "Filter",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun DesignRoundedSquareIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
    }
}

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
            .background(MaterialTheme.colorScheme.surface),
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

@Composable
fun DesignRoundedImage(
    modifier: Modifier = Modifier,
    contentDescription: String,
    bitmap: Bitmap?
) {
    val imgModifier = modifier.clip(CircleShape)
    bitmap?.let { image ->
        Image(
            modifier = imgModifier,
            bitmap = image.asImageBitmap(),
            contentDescription = contentDescription
        )
    } ?: Image(
        modifier = imgModifier,
        painter = painterResource(R.drawable.no_user_image),
        contentDescription = contentDescription
    )
}

@Composable
fun DesignBottomBar(
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.EditAttributes,
                    contentDescription = "Buttons",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

