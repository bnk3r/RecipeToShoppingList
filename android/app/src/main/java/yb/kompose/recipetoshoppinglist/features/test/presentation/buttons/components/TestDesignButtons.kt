package yb.kompose.recipetoshoppinglist.features.test.presentation.buttons.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.DesignButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.DesignIconButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.DesignIconButtonStyle
import yb.kompose.recipetoshoppinglist.features.core.presentation.components.button.OutlinedDesignButton
import yb.kompose.recipetoshoppinglist.features.core.presentation.theme.RedDark

@Composable
fun TestDesignButtons(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DesignButton(text = "Design Button Enabled", onClick = {})
        DesignButton(text = "Design Button Disabled", onClick = {}, enabled = false)
        OutlinedDesignButton(text = "Outlined Design Button Enabled", onClick = {})
        OutlinedDesignButton(
            text = "Outlined Design Button Disabled",
            onClick = {},
            enabled = false
        )
        DesignButton(
            onClick = {},
            text = "Button With Icon Enabled",
            imageVector = Icons.Default.Person
        )
        DesignButton(
            onClick = {},
            text = "Button With Icon Disabled",
            imageVector = Icons.Default.Person,
            enabled = false
        )
        OutlinedDesignButton(
            onClick = {},
            text = "Outlined With Icon Enabled",
            imageVector = Icons.AutoMirrored.Filled.Message
        )
        OutlinedDesignButton(
            onClick = {},
            text = "Outlined With Icon Disabled",
            enabled = false,
            imageVector = Icons.AutoMirrored.Filled.Message
        )
        DesignIconButton(
            onClick = {},
            imageVector = Icons.Default.Mail,
            style = DesignIconButtonStyle.PRIMARY,
            contentDescription = "Mail"
        )
        DesignIconButton(
            onClick = {},
            imageVector = Icons.Default.Mail,
            style = DesignIconButtonStyle.PRIMARY,
            contentDescription = "Mail",
            enabled = false
        )
        DesignIconButton(
            onClick = {},
            imageVector = Icons.Default.Share,
            style = DesignIconButtonStyle.WHITE,
            contentDescription = "Share"
        )
        DesignIconButton(
            onClick = {},
            imageVector = Icons.Default.Share,
            style = DesignIconButtonStyle.WHITE,
            contentDescription = "Share",
            enabled = false
        )
        DesignIconButton(
            onClick = {},
            imageVector = Icons.Default.Favorite,
            style = DesignIconButtonStyle.CUSTOM,
            customColors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = RedDark,
                disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                disabledContentColor = MaterialTheme.colorScheme.onTertiary
            ),
            contentDescription = "Mail"
        )
        DesignIconButton(
            onClick = {},
            imageVector = Icons.Default.Favorite,
            style = DesignIconButtonStyle.CUSTOM,
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