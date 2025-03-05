package yb.kompose.recipetoshoppinglist.features.core.presentation.components.image

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import yb.kompose.recipetoshoppinglist.R

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