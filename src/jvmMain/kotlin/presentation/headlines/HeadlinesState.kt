package presentation.headlines

import domain.models.CountryCode
import presentation.headlines.models.ArticleUI

data class HeadlinesState(
    val isLoading: Boolean = false,
    val headline: ArticleUI? = null,
    val articles: List<ArticleUI> = emptyList(),
    val searchQuery: String = "",
    val title: String = "Headlines",
    val countries: List<CountryCode> = CountryCode.values().toList(),
    val selectedTabIndex: Int = 0,
)
