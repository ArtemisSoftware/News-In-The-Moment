package presentation.headlines

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import presentation.headlines.composables.HeadlineBackLayer
import presentation.headlines.composables.HeadlineFrontLayer

@Composable
fun HeadlineScreen(){

    val viewModel = remember { HeadlinesViewModel() }
    val state = viewModel.state

    HeadlineContent(
        state = state,
        events = viewModel::onTriggerEvent,
    )

}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HeadlineContent(
    state: HeadlinesState,
    events: (HeadlinesEvents) -> Unit,
){
    val scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val scope = rememberCoroutineScope()

    BackdropScaffold(
        scaffoldState = scaffoldState,
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
        frontLayerContent = {
            HeadlineFrontLayer(
                isLoading = state.isLoading,
                articles = state.articles,
                headline = state.headline,
                openUrl = { url ->
                    events.invoke(HeadlinesEvents.OpenUrl(url = url))
                }
            )
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
    )
}