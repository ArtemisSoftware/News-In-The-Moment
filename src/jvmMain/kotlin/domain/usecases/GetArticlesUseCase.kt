package domain.usecases

import domain.models.CountryCode
import domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import util.ApiConstants.DEFAULT_COUNTRY_CODE

class GetArticlesUseCase(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(country: CountryCode, topics: List<String>) = withContext(Dispatchers.IO){

        val articles = topics
            .map { topic -> async { topic to newsRepository.getTopHeadlines(country = country.code, topic = topic) } }
            .map { it.await() }
            .toMap()

        articles
    }
}