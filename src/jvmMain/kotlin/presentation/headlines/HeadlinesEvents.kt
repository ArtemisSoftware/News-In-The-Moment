package presentation.headlines

import domain.models.CountryCode

sealed class HeadlinesEvents{
    data class UpdateSearchQuery(val query: String): HeadlinesEvents()
    object Search : HeadlinesEvents()
    data class GetArticles(val countryCode: CountryCode): HeadlinesEvents()

    data class OpenUrl(val url: String?): HeadlinesEvents()
}
