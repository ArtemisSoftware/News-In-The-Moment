package presentation.headlines.composables

import DummyData
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import domain.models.ArticleType
import domain.models.News
import presentation.headlines.HeadlinesEvents

@Composable
fun HeadlinePage(
    imageLib: HashMap<String?, ImageBitmap> = HashMap(),
    events: (HeadlinesEvents) -> Unit,
    news: News,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            news.headline?.let {
                item(span = { GridItemSpan(2) }) {
                    ArticleCard(
                        article = it,
                        type = ArticleType.HEADLINE,
                        imageSize = 300.dp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        imageLib = imageLib,
                        updateImageLib = { url, image ->
                            events.invoke(HeadlinesEvents.UpdateImageLib(url = url, image = image))
                        },
                        onClick = { url ->
                            url?.let { events.invoke(HeadlinesEvents.OpenUrl(url = it)) }
                        },
                    )
                }
            }

            items(news.articles) { article ->
                ArticleCard(
                    article = article,
                    type = ArticleType.STANDARD,
                    modifier = Modifier.fillMaxWidth(),
                    imageLib = imageLib,
                    updateImageLib = { url, image ->
                        events.invoke(HeadlinesEvents.UpdateImageLib(url = url, image = image))
                    },
                    onClick = { url ->
                        url?.let { events.invoke(HeadlinesEvents.OpenUrl(url = it)) }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun HeadlinePagePreview() {
    HeadlinePage(
        news = DummyData.news,
        imageLib = HashMap(),
        events = { },
    )
}
