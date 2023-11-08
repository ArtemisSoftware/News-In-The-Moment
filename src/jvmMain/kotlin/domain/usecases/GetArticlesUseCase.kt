package domain.usecases

import data.remote.exceptions.NewsException
import domain.Resource
import domain.models.CountryCode
import domain.models.News
import domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetArticlesUseCase(
    private val newsRepository: NewsRepository,
) {

    suspend operator fun invoke(country: CountryCode, topics: List<String>) = withContext(Dispatchers.IO) {
        val results = topics
            .map { topic ->
                async {
                    try {
                        val result = newsRepository.getTopHeadlines(country = country.code, topic = topic)
                        Resource.Success(result)
                    } catch (e: NewsException) {
                        Resource.Error(e.message)
                    }
                }
            }
            .map { it.await() }

        val news = results.filterIsInstance<Resource.Success<News>>().map { it.data }

        if (news.isNotEmpty()) {
            Resource.Success(news)
        } else {
            val error = results.filterIsInstance<Resource.Error>().first()
            error
        }
    }
}
