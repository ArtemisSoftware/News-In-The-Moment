package presentation.headlines.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.models.CountryCode
import presentation.headlines.HeadlinesEvents
import util.CursorUtils.handCursor

@Composable
fun HeadlineBackLayer(
    countries: List<CountryCode>,
    searchQuery: String,
    events: (HeadlinesEvents) -> Unit,
    closeMenu: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.4F)
                .padding(top = 24.dp)
                .padding(vertical = 24.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Search...") },
                value = searchQuery,
                onValueChange = { events.invoke(HeadlinesEvents.UpdateSearchQuery(query = it)) },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            events.invoke(HeadlinesEvents.Search)
                            closeMenu.invoke()
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .pointerHoverIcon(handCursor()),
                        content = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search button",
                                tint = Color.Black,
                            )
                        },
                    )
                },
            )

            Text(
                text = "Countries",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )

            Divider(modifier = Modifier.width(50.dp))

            LazyColumn() {
                items(countries) { countryCode ->
                    TextButton(
                        onClick = {
                            events.invoke(HeadlinesEvents.GetArticles(countryCode = countryCode))
                            closeMenu()
                        },
                        content = {
                            Text(
                                text = countryCode.description,
                                fontSize = 16.sp,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HeadlineBackLayerPreview() {
    HeadlineBackLayer(
        searchQuery = "Search",
        countries = listOf(CountryCode.USA),
        events = {},
        closeMenu = {},
    )
}
