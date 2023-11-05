package presentation.headlines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import domain.models.CountryCode
import domain.models.News
import domain.usecases.GetArticlesUseCase
import domain.usecases.GetTopicsUseCase
import domain.usecases.SearchArticlesUseCase
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import presentation.headlines.models.TabItem
import util.UrlUtils.openURL

class HeadlinesViewModel(
    private val getArticlesUseCase: GetArticlesUseCase = KoinJavaComponent.get(GetArticlesUseCase::class.java),
    private val searchArticlesUseCase: SearchArticlesUseCase = KoinJavaComponent.get(SearchArticlesUseCase::class.java),
    private val getTopicsUseCase: GetTopicsUseCase = KoinJavaComponent.get(GetTopicsUseCase::class.java),
) {
    private val _state = MutableStateFlow(HeadlinesState())
    val state: StateFlow<HeadlinesState> = _state.asStateFlow()

    private var coroutineScope = CoroutineScope(Dispatchers.IO)

    private val placeHolder = useResource("no_image.png") {
        loadImageBitmap(it)
    }
    private val topics = getTopicsUseCase()
    val tabItems = topics.map { TabItem(title = it) }
    var imageLib = mutableStateOf(hashMapOf<String?, ImageBitmap>())
        private set

    init {
        imageLib.value["no_image"] = placeHolder
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

            is HeadlinesEvents.UpdateImageLib -> {
                imageLib.value[events.url] = events.image ?: placeHolder
            }

            HeadlinesEvents.Refresh -> {
                refresh()
            }
        }
    }

    private fun updateSearchText(text: String) = with(_state) {
        update {
            it.copy(
                searchQuery = text,
            )
        }
    }

    private fun openUrl(url: String?) {
        if (!url.isNullOrEmpty()) {
            openURL(url)
        }
    }

    private fun selectTab(index: Int) = with(_state) {
        update {
            it.copy(
                selectedTabIndex = index,
            )
        }
    }

    private fun updateSearch(
        isLoading: Boolean,
        isSearching: Boolean,
        searchQuery: String = "",
        countryCode: CountryCode,
        subtitle: String,
    ) = with(_state) {
        update {
            it.copy(
                isLoading = isLoading,
                isSearching = isSearching,
                searchQuery = searchQuery,
                countryCode = countryCode,
                title = "Headlines $subtitle",
            )
        }
    }

    private fun updateHeadlines(news: List<News>) = with(_state) {
        update {
            it.copy(
                news = news,
                isLoading = false,
            )
        }
    }

    private fun getHeadlines(country: CountryCode) {
        try {
            updateSearch(
                isLoading = true,
                isSearching = false,
                countryCode = country,
                subtitle = country.description,
            )

            coroutineScope.launch {
                val result = getArticlesUseCase(country, topics)
                updateHeadlines(news = result)
            }
        } catch (e: ClientRequestException) {
            println("Error fetching data: ${e.message}")
        }
    }

    private fun searchArticles() = with(_state.value) {
        try {
            updateSearch(
                isLoading = true,
                isSearching = true,
                countryCode = countryCode,
                subtitle = searchQuery.capitalize(Locale.current),
            )

            coroutineScope.launch {
                val result = searchArticlesUseCase(searchQuery)
                updateHeadlines(news = listOf(result))
            }
        } catch (e: ClientRequestException) {
            println("Error fetching data: ${e.message}")
        }
    }

    private fun refresh() = with(_state.value) {
        if (isSearching) {
            searchArticles()
        } else {
            getHeadlines(countryCode)
        }
    }
}
