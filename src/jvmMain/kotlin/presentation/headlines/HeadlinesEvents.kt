package presentation.headlines

import androidx.compose.ui.graphics.ImageBitmap
import domain.models.CountryCode

sealed class HeadlinesEvents {
    data class UpdateSearchQuery(val query: String) : HeadlinesEvents()
    object Search : HeadlinesEvents()
    data class GetArticles(val countryCode: CountryCode) : HeadlinesEvents()
    data class OpenUrl(val url: String?) : HeadlinesEvents()
    data class SelectTab(val index: Int) : HeadlinesEvents()

    data class UpdateImageLib(val url: String, val image: ImageBitmap? = null) : HeadlinesEvents()
}
