package domain.usecases

import domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import util.ApiConstants.DEFAULT_COUNTRY_CODE

class GetArticlesUseCase(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(country: String = DEFAULT_COUNTRY_CODE) = withContext(Dispatchers.IO){
        newsRepository.getTopHeadlines(country = country)
    }
}