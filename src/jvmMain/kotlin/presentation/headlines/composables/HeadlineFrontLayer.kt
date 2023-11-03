package presentation.headlines.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.headlines.HeadlinesEvents
import presentation.headlines.HeadlinesState
import presentation.headlines.models.TabItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeadlineFrontLayer(
    state: HeadlinesState,
    tabs: List<TabItem>,
    imageLib: HashMap<String?, ImageBitmap> = HashMap(),
    events: (HeadlinesEvents) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = state.selectedTabIndex) {
        tabs.size
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            events.invoke(HeadlinesEvents.SelectTab(index = pagerState.currentPage))
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TabRow(
            selectedTabIndex = state.selectedTabIndex,
        ) {
            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    modifier = Modifier.height(80.dp),
                    selected = (index == state.selectedTabIndex),
                    onClick = {
                        events.invoke(HeadlinesEvents.SelectTab(index = index))
                    },
                    text = {
                        Text(
                            text = tabItem.title.capitalize(Locale.current),
                            fontSize = 16.sp,
                        )
                    },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
        ) { index ->
            HeadlinePage(
                news = state.news[pagerState.currentPage],
                imageLib = imageLib,
                events = events,
            )
        }
    }
}

@Preview
@Composable
private fun HeadlineFrontLayerPreview() {
    HeadlineFrontLayer(
        state = HeadlinesState(
            title = "The title",
        ),
        imageLib = HashMap(),
        events = {},
        tabs = listOf(),
    )
}
