package domain.usecases

import domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchArticlesUseCase(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(query: String) = withContext(Dispatchers.IO){
        newsRepository.searchNews(query = query)
    }
}