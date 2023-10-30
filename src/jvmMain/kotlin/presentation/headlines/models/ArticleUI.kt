package presentation.headlines.models

import androidx.compose.ui.graphics.ImageBitmap
import domain.models.ArticleType

data class ArticleUI(
    val type: ArticleType,
    val postUrl: String? = null,
    val imageUrl: String? = null,
    val title: String,
    val content: String,
    val source: String,
    val default: ImageBitmap,
)
