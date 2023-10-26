package data.repository

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import data.mappers.toArticle
import data.remote.NewsApiClient
import domain.models.Article
import domain.repository.NewsRepository

class NewsRepositoryImpl() : NewsRepository {

    private val defaultImage: ImageBitmap = useResource("no_image.png") {
        loadImageBitmap(it)
    }


    override suspend fun getTopHeadlines(country: String): List<Article> {
        val news = NewsApiClient.getTopHeadlines(country = country)
        return news.articles.map { it.toArticle() }
    }

    override suspend fun searchNews(query: String): List<Article> {
        val news = NewsApiClient.getSearchedNews(query)
        return news.articles.map { it.toArticle() }
    }
}