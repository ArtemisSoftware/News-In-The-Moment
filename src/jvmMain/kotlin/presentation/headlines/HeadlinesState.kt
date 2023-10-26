package presentation.headlines

import presentation.headlines.mappers.ArticleUI

data class HeadlinesState(
    val headline: ArticleUI? = null,
    val articles: List<ArticleUI> = emptyList(),
)
