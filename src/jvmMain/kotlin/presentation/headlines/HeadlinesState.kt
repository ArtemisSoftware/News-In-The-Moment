package presentation.headlines

import domain.models.CountryCode
import domain.models.News

data class HeadlinesState(
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val news: List<News> = emptyList(),
    val searchQuery: String = "",
    val title: String = "Headlines",
    val countries: List<CountryCode> = CountryCode.values().toList(),
    val selectedTabIndex: Int = 0,
)
