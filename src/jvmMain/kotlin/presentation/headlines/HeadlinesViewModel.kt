package presentation.headlines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import domain.models.ArticleType
import domain.models.CountryCode
import domain.usecases.GetArticlesUseCase
import domain.usecases.GetTopicsUseCase
import domain.usecases.SearchArticlesUseCase
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import presentation.headlines.mappers.toNews
import presentation.headlines.mappers.toUI
import presentation.headlines.models.TabItem
import util.UrlUtils.openURL

class HeadlinesViewModel(
    private val getArticlesUseCase: GetArticlesUseCase = KoinJavaComponent.get(GetArticlesUseCase::class.java),
    private val searchArticlesUseCase: SearchArticlesUseCase = KoinJavaComponent.get(SearchArticlesUseCase::class.java),
    private val getTopicsUseCase: GetTopicsUseCase = KoinJavaComponent.get(GetTopicsUseCase::class.java),
) {
    var state by mutableStateOf(HeadlinesState())
    private var coroutineScope = CoroutineScope(Dispatchers.IO)

    private var defaultImage: ImageBitmap = useResource("no_image.png") {
        loadImageBitmap(it)
    }

    private val topics = getTopicsUseCase()
    val tabItems = topics.map { TabItem(title = it) }

    init {
        getHeadlines(CountryCode.USA)
    }

    fun onTriggerEvent(events: HeadlinesEvents) {
        when (events) {
            HeadlinesEvents.Search -> {
                searchArticles()
            }
            is HeadlinesEvents.UpdateSearchQuery -> {
                updateSearchText(events.query)
            }

            is HeadlinesEvents.GetArticles -> {
                getHeadlines(country = events.countryCode)
            }

            is HeadlinesEvents.OpenUrl -> {
                openUrl(events.url)
            }

            is HeadlinesEvents.SelectTab -> {
                selectTab(events.index)
            }
        }
    }

    private fun updateSearchText(text: String) {
        state = state.copy(
            searchQuery = text,
        )
    }

    private fun updateTitle(subtitle: String) {
        state = state.copy(
            title = "Headlines $subtitle",
        )
    }

    private fun openUrl(url: String?) {
        if (!url.isNullOrEmpty()) {
            openURL(url)
        }
    }

    private fun selectTab(index: Int) {
        state = state.copy(
            selectedTabIndex = index,
        )
    }

    private fun getHeadlines(country: CountryCode) {
        try {
            state = state.copy(isLoading = true)
            updateTitle(country.description)

            coroutineScope.launch {
                val result = getArticlesUseCase(country, topics)
                val headline = result.get(topics.first())!!.first()
                val articles = result.get(topics.first())!!.drop(1)
                state = state.copy(
                    news = result.toNews(defaultImage = defaultImage),
                )
                state = state.copy(
                    headline = headline.toUI(type = ArticleType.HEADLINE, defaultImage = defaultImage),
                )
                state = state.copy(
                    articles = articles.map { it.toUI(defaultImage = defaultImage) },
                )
//                state = state.copy(
//                    headline = headline.toUI(type = ArticleType.HEADLINE, defaultImage = defaultImage),
//                    articles = articles.map{ it.toUI(defaultImage = defaultImage) }
//                )

                state = state.copy(isLoading = false)
            }
        } catch (e: ClientRequestException) {
            println("Error fetching data: ${e.message}")
        }
    }

    private fun searchArticles() {
        try {
            state = state.copy(isLoading = true)
            updateTitle(state.searchQuery.capitalize(Locale.current))

            coroutineScope.launch {
                val result = searchArticlesUseCase(state.searchQuery)
                val headline = result.first()
                val articles = result.drop(1)
                state = state.copy(
                    headline = headline.toUI(type = ArticleType.HEADLINE, defaultImage = defaultImage),
                )
                state = state.copy(
                    articles = articles.map { it.toUI(defaultImage = defaultImage) },
                )
                state = state.copy(isLoading = false)
            }
        } catch (e: ClientRequestException) {
            println("Error fetching data: ${e.message}")
        }
    }
}
