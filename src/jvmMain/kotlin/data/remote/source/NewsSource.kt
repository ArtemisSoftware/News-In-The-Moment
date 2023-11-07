package data.remote.source

import data.remote.HandleApi
import data.remote.NewsApiClient
import data.remote.dto.NewsDto

class NewsSource {

    suspend fun getTopHeadlines(country: String, topic: String): NewsDto {
        return HandleApi.safeApiCall { NewsApiClient.getTopHeadlines(country = country, topic = topic) }
    }

    suspend fun searchNews(query: String): NewsDto {
        return HandleApi.safeApiCall { NewsApiClient.getSearchedNews(query) }
    }
}
