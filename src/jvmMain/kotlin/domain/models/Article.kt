package domain.models

data class Article(
    val postUrl: String? = null,
    val imageUrl: String? = null,
    val title: String,
    val content: String,
    val source: String,
)
