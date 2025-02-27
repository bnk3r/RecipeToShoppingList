package yb.kompose.recipetoshoppinglist.features.core.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.TagFaces
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import yb.kompose.recipetoshoppinglist.R
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.ProfileDestination
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.RecipesDestination
import yb.kompose.recipetoshoppinglist.features.core.presentation.navigation.models.ShoppingListsDestination

@Composable
fun BottomNavBar(
    navController: NavController,
    onClickShoppingLists: () -> Unit,
    onClickRecipes: () -> Unit,
    onClickProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 4.dp,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ShoppingListsNavIcon(
                selected = currentDestination?.hasRoute(ShoppingListsDestination::class) == true,
                onClick = onClickShoppingLists
            )
            RecipesNavIcon(
                selected = currentDestination?.hasRoute(RecipesDestination::class) == true,
                onClick = onClickRecipes
            )
            ProfileNavIcon(
                selected = currentDestination?.hasRoute(ProfileDestination::class) == true,
                onClick = onClickProfile
            )
        }
    }
}

@Composable
fun ShoppingListsNavIcon(
    selected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = when {
                selected -> Icons.Default.ShoppingCart
                else -> Icons.Outlined.ShoppingCart
            },
            contentDescription = stringResource(R.string.shopping_lists),
            tint = when {
                selected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

@Composable
fun RecipesNavIcon(
    selected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = when {
                selected -> Icons.Default.Fastfood
                else -> Icons.Outlined.Fastfood
            },
            contentDescription = stringResource(R.string.recipes),
            tint = when {
                selected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

@Composable
fun ProfileNavIcon(
    selected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = when {
                selected -> Icons.Default.TagFaces
                else -> Icons.Outlined.TagFaces
            },
            contentDescription = stringResource(R.string.profile),
            tint = when {
                selected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    val navController = rememberNavController()
    BottomNavBar(
        navController = navController,
        onClickShoppingLists = {},
        onClickRecipes = {},
        onClickProfile = {},
        modifier = Modifier.fillMaxWidth()
    )
}