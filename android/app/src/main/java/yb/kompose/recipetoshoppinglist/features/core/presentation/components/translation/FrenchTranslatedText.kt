package yb.kompose.recipetoshoppinglist.features.core.presentation.components.translation

import android.util.Log
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FrenchTranslatedText(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var translatedText by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val englishFrenchTranslator = Translation.getClient(
                TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.ENGLISH)
                    .setTargetLanguage(TranslateLanguage.FRENCH)
                    .build()
            )
            val conditions = DownloadConditions.Builder().requireWifi().build()
            try {
                englishFrenchTranslator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener {
                        englishFrenchTranslator.translate(text)
                            .addOnSuccessListener { translation ->
                                translatedText = translation
                                englishFrenchTranslator.close()
                            }
                    }

            } catch (e: Exception) {
                Log.e("Translation", "Error : ${e.message}")
                englishFrenchTranslator.close()
            }
        }
    }

    Text(
        modifier = modifier,
        textAlign = textAlign,
        color = color,
        style = style,
        text = when {
            translatedText == null -> text
            else -> translatedText!!
        }
    )
}