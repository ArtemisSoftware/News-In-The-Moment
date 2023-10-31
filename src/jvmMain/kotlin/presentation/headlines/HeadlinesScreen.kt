package presentation.headlines

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.material3.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import presentation.headlines.composables.HeadlineBackLayer
import presentation.headlines.composables.HeadlineFrontLayer
import presentation.headlines.models.TabItem

@Composable
fun HeadlineScreen(){
    val viewModel = remember { HeadlinesViewModel() }
    val state = viewModel.state

    HeadlineContent(
        tabs = viewModel.tabItems,
        state = state,
        events = viewModel::onTriggerEvent,
    )
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun HeadlineContent(
    state: HeadlinesState,
    events: (HeadlinesEvents) -> Unit,
    tabs: List<TabItem>,
){
    val scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val scope = rememberCoroutineScope()
    var pagerState = rememberPagerState(initialPage = state.selectedTabIndex) {
        tabs.size
    }

    LaunchedEffect(state.selectedTabIndex){
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress){
        if(!pagerState.isScrollInProgress) {
            events.invoke(HeadlinesEvents.SelectTab(index = pagerState.currentPage))
        }
    }

    BackdropScaffold(
        scaffoldState = scaffoldState,
        peekHeight = 80.dp,
        appBar =  {
            TopAppBar(
                title = {
                    Text(
                        text = state.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                },
                navigationIcon = {
                    if (scaffoldState.isConcealed) {
                        FilledIconButton(
                            onClick = {},
                            content = {
                                Icon(
                                    imageVector = Icons.Outlined.List,
                                    contentDescription = "Localized description",
                                )
                            }
                        )
                    } else {
                        IconButton(
                            onClick = {
//                                scope.launch { scaffoldState.conceal()}
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                },
//                elevation = 0.dp,
//                backgroundColor = Color.Transparent
            )
        },
        backLayerBackgroundColor = MaterialTheme.colorScheme.background,
        backLayerContent = {
            HeadlineBackLayer(
                countries = state.countries,
                events = events,
                searchQuery = state.searchQuery,
                closeMenu = {
                    scope.launch { scaffoldState.conceal() }
                },
            )
        },
        frontLayerElevation = 12.dp,
        frontLayerContent = {
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                ){
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
                        .weight(1F)
                ){ index ->
                    HeadlineFrontLayer(
                        isLoading = state.isLoading,
                        articles = state.articles,
                        headline = state.headline,
                        openUrl = { url ->
                            events.invoke(HeadlinesEvents.OpenUrl(url = url))
                        }
                    )
                }
            }


        },
    )
}

@Preview
@Composable
private fun HeadlineContentPreview() {
    HeadlineContent(
        state = HeadlinesState(
            articles = DummyData.articles,
            headline = DummyData.articles.first(),
            title = "The title",
        ),
        events = {},
        tabs = listOf(),
    )
}