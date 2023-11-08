package domain.models

data class News(
    val topic: String,
    val headline: Article? = null,
    val articles: List<Article> = emptyList(),
)
