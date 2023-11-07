package domain.usecases

import data.remote.exceptions.NewsException
import domain.Resource
import domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchArticlesUseCase(
    private val newsRepository: NewsRepository,
) {

    suspend operator fun invoke(query: String) = withContext(Dispatchers.IO) {
        try {
            val result = newsRepository.searchNews(query = query)
            Resource.Success(listOf(result))
        } catch (e: NewsException) {
            Resource.Error(e.message)
        }
    }
}
