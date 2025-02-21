package yb.kompose.recipetoshoppinglist.features.test.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.DesignButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.IconDesignButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.IconDesignButtonStyle
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.OutlinedDesignButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.RedDark

@Composable
fun TestDesign() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff2B2D30))
            .padding(vertical = 60.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { DesignButton(text = "Design Button Enabled", onClick = {}) }
        item { DesignButton(text = "Design Button Disabled", onClick = {}, enabled = false) }
        item { OutlinedDesignButton(text = "Outlined Design Button Enabled", onClick = {}) }
        item {
            OutlinedDesignButton(
                text = "Outlined Design Button Disabled",
                onClick = {},
                enabled = false
            )
        }
        item {
            DesignButton(
                onClick = {},
                text = "Button With Icon Enabled",
                imageVector = Icons.Default.Person
            )
        }
        item {
            DesignButton(
                onClick = {},
                text = "Button With Icon Disabled",
                imageVector = Icons.Default.Person,
                enabled = false
            )
        }
        item {
            OutlinedDesignButton(
                onClick = {},
                text = "Outlined With Icon Enabled",
                imageVector = Icons.AutoMirrored.Filled.Message
            )
        }
        item {
            OutlinedDesignButton(
                onClick = {},
                text = "Outlined With Icon Disabled",
                enabled = false,
                imageVector = Icons.AutoMirrored.Filled.Message
            )
        }
        item {
            IconDesignButton(
                onClick = {},
                imageVector = Icons.Default.Mail,
                style = IconDesignButtonStyle.PRIMARY,
                contentDescription = "Mail"
            )
        }
        item {
            IconDesignButton(
                onClick = {},
                imageVector = Icons.Default.Mail,
                style = IconDesignButtonStyle.PRIMARY,
                contentDescription = "Mail",
                enabled = false
            )
        }
        item {
            IconDesignButton(
                onClick = {},
                imageVector = Icons.Default.Share,
                style = IconDesignButtonStyle.WHITE,
                contentDescription = "Share"
            )
        }
        item {
            IconDesignButton(
                onClick = {},
                imageVector = Icons.Default.Share,
                style = IconDesignButtonStyle.WHITE,
                contentDescription = "Share",
                enabled = false
            )
        }
        item {
            IconDesignButton(
                onClick = {},
                imageVector = Icons.Default.Favorite,
                style = IconDesignButtonStyle.CUSTOM,
                customColors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = RedDark,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                    disabledContentColor = MaterialTheme.colorScheme.onTertiary
                ),
                contentDescription = "Mail"
            )
        }
        item {
            IconDesignButton(
                onClick = {},
                imageVector = Icons.Default.Favorite,
                style = IconDesignButtonStyle.CUSTOM,
                customColors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = RedDark,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                    disabledContentColor = MaterialTheme.colorScheme.onTertiary
                ),
                contentDescription = "Mail",
                enabled = false
            )
        }
    }
}