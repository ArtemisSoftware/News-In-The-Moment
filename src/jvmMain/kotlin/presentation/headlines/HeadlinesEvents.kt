package presentation.headlines

sealed class HeadlinesEvents{
    data class UpdateSearchQuery(val query: String): HeadlinesEvents()
    object Search : HeadlinesEvents()
    data class GetArticles(val countryCode: String): HeadlinesEvents()
}
