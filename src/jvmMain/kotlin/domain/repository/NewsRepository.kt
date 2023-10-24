package domain.repository

import domain.models.Article

interface NewsRepository {

    suspend fun getTopHeadlines(): List<Article>

    suspend fun searchNews(query: String): List<Article>
}