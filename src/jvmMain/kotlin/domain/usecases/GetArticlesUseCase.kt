package domain.usecases

import domain.models.CountryCode
import domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetArticlesUseCase(
    private val newsRepository: NewsRepository,
) {

    suspend operator fun invoke(country: CountryCode, topics: List<String>) = withContext(Dispatchers.IO) {
        val news = topics
            .map { topic -> async { newsRepository.getTopHeadlines(country = country.code, topic = topic) } }
            .map { it.await() }

        news
    }
}
