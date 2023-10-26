package domain.repository

import domain.models.Article

interface NewsRepository {

    suspend fun getTopHeadlines(country: String): List<Article>

    suspend fun searchNews(query: String): List<Article>
}