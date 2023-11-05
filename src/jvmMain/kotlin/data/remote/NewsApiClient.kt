package data.remote

import data.logging.HttpLogger
import data.remote.dto.NewsDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import util.Constants

object NewsApiClient {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            logger = HttpLogger()
            level = LogLevel.ALL
        }
    }

    suspend fun getTopHeadlines(country: String, topic: String): NewsDto {
        val url = "https://newsapi.org/v2/top-headlines?category=$topic&country=$country&apiKey=${Constants.API_KEY}"
        return client.get(url).body()
    }

    suspend fun getSearchedNews(searchedText: String): NewsDto {
        val url = "https://newsapi.org/v2/everything?q=$searchedText&apiKey=${Constants.API_KEY}"
        return client.get(url).body()
    }
}
