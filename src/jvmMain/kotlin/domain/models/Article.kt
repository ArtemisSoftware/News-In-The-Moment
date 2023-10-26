package domain.models

import androidx.compose.ui.graphics.ImageBitmap

data class Article(
    val url: String? = null,
    val imageUrl: String? = null,
    val title: String,
    val content: String,
    val source: String,
)
