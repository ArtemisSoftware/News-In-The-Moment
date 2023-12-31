package presentation.headlines

import domain.models.CountryCode
import domain.models.News
import presentation.headlines.models.TabItem

data class HeadlinesState(
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val countryCode: CountryCode = CountryCode.USA,
    val news: List<News> = emptyList(),
    val searchQuery: String = "",
    val lastSearchQuery: String? = null,
    val title: String = "Headlines",
    val countries: List<CountryCode> = CountryCode.values().toList(),
    val selectedTabIndex: Int = 0,
    val refreshing: Boolean = false,
    val error: String? = null,
    val tabs: List<TabItem> = emptyList(),
)
