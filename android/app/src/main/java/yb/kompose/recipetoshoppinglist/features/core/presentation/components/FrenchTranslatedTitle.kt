package yb.kompose.recipetoshoppinglist.features.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun FrenchTranslatedTitle(
    title: String,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    FrenchTranslatedText(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = color,
        textAlign = textAlign,
        modifier = modifier
    )
}