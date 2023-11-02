package presentation.headlines.composables

import DummyData
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import presentation.headlines.models.News

@Composable
fun HeadlinePage(
    news: News,
    openUrl: (String?) -> Unit,
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
                        imageSize = 300.dp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = openUrl,
                    )
                }
            }

            items(news.articles) { article ->
                ArticleCard(
                    article = article,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = openUrl,
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
        openUrl = { },
    )
}
