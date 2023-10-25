package presentation.headlines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import data.repository.NewsRepositoryImpl
import domain.models.Article
import domain.repository.NewsRepository
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class Headlines(newsRepository: NewsRepositoryImpl = KoinJavaComponent.get(NewsRepository::class.java)) {

    var searchedText by mutableStateOf("")
    var title by mutableStateOf("Headlines")
    var artilcles by mutableStateOf<List<Article>>(emptyList())
    private var coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        try {
            coroutineScope.launch {
                artilcles = newsRepository.getTopHeadlines()
            }
        }catch (e: ClientRequestException) {
            println("Error fetching data: ${e.message}")
        }
    }

    fun updateSearchText(text: String) {
        searchedText = text
    }
}