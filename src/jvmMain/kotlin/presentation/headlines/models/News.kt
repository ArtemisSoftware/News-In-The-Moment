package presentation.headlines.models

data class News(
    val headline: ArticleUI? = null,
    val articles: List<ArticleUI> = emptyList(),
)
