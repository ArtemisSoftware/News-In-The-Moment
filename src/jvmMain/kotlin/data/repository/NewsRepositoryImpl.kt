package data.repository

import data.mappers.toArticle
import data.remote.NewsApiClient
import domain.models.Article
import domain.repository.NewsRepository

class NewsRepositoryImpl() : NewsRepository {
    override suspend fun getTopHeadlines(): List<Article> {
        val news = NewsApiClient.getTopHeadlines()
        return news.articles.map { it.toArticle() }
    }

    override suspend fun searchNews(query: String): List<Article> {
        val news = NewsApiClient.getSearchedNews(query)
        return news.articles.map { it.toArticle() }
    }
}