package domain.repository

import domain.models.News

interface NewsRepository {

    suspend fun getTopHeadlines(country: String, topic: String): News

    suspend fun searchNews(query: String): News
}
