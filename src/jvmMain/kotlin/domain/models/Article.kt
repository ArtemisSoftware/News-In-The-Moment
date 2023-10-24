package domain.models

data class Article(
    val url: String? = null,
    val imageUrl: String? = null,
    val title: String,
    val content: String,
)
