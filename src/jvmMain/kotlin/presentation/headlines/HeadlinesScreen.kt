package presentation.headlines

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import presentation.headlines.composables.HeadlineBackLayer
import presentation.headlines.composables.HeadlineFrontLayer
import presentation.headlines.composables.HeadlinePage
import presentation.headlines.models.TabItem

@Composable
fun HeadlineScreen(viewModel: HeadlinesViewModel = remember { HeadlinesViewModel() }) {
    val state = viewModel.state.collectAsState()

    HeadlineContent(
        tabs = viewModel.tabItems,
        state = state.value,
        imageLib = viewModel.imageLib.value,
        events = viewModel::onTriggerEvent,
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HeadlineContent(
    state: HeadlinesState,
    events: (HeadlinesEvents) -> Unit,
    tabs: List<TabItem>,
    imageLib: HashMap<String?, ImageBitmap>,
) {
    val scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val scope = rememberCoroutineScope()

    BackdropScaffold(
        scaffoldState = scaffoldState,
        peekHeight = 80.dp,
        appBar = {
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
                            onClick = {
                                events.invoke(HeadlinesEvents.Refresh)
                            },
                            content = {
                                Icon(
                                    imageVector = Icons.Outlined.Refresh,
                                    contentDescription = "Localized description",
                                )
                            },
                        )
                    } else {
                        IconButton(
                            onClick = { scope.launch { scaffoldState.conceal() } },
                            content = {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Close",
                                )
                            },
                        )
                    }
                },
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
            if (state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize().background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Loading...")
                }
            } else if (!state.error.isNullOrEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(state.error)
                }
            } else {
                if (state.isSearching) {
                    HeadlinePage(
                        news = state.news.first(),
                        events = events,
                        imageLib = imageLib,
                    )
                } else {
//                    HeadlineFrontLayer(
//                        state = state,
//                        events = events,
//                        tabs = tabs,
//                        imageLib = imageLib,
//                    )
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
            title = "The title",
        ),
        events = {},
        tabs = listOf(),
        imageLib = HashMap(),
    )
}
