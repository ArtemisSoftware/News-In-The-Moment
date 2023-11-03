package data.repository

import data.mappers.toNews
import data.remote.NewsApiClient
import domain.models.News
import domain.repository.NewsRepository

class NewsRepositoryImpl() : NewsRepository {

    override suspend fun getTopHeadlines(country: String, topic: String): News {
        val news = NewsApiClient.getTopHeadlines(country = country, topic = topic)
        return news.articles.filter { it.content != "[Removed]" }.toNews()
    }

    override suspend fun searchNews(query: String): News {
        val news = NewsApiClient.getSearchedNews(query)
        return news.articles.filter { it.content != "[Removed]" }.toNews()
    }
}
