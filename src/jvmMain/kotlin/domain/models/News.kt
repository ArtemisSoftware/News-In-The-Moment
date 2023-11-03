package domain.models

data class News(
    val headline: Article? = null,
    val articles: List<Article> = emptyList(),
)
