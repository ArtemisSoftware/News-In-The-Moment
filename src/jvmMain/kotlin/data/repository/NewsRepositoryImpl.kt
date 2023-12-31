package data.repository

import data.mappers.toNews
import data.remote.NewsApiClient
import data.remote.source.NewsSource
import domain.models.News
import domain.repository.NewsRepository

class NewsRepositoryImpl(private val newsSource: NewsSource) : NewsRepository {

    override suspend fun getTopHeadlines(country: String, topic: String): News {
        val news = newsSource.getTopHeadlines(country = country, topic = topic)
        //val news = NewsApiClient.getTopHeadlines(country = country, topic = topic)
        return news.articles.toNews(topic = topic)
    }

    override suspend fun searchNews(query: String): News {
        val news = newsSource.searchNews(query)
        return news.articles.toNews(topic = query)
    }
}
