package presentation.headlines.mappers

import androidx.compose.ui.graphics.ImageBitmap
import domain.models.ArticleType

data class ArticleUI(
    val type: ArticleType,
    val url: String? = null,
    val title: String,
    val content: String,
    val source: String,
    val image: ImageBitmap,
)
