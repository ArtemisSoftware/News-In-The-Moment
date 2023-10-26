package presentation.headlines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.repository.NewsRepositoryImpl
import domain.models.Article
import domain.repository.NewsRepository
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import presentation.headlines.composables.ArticleCard
import presentation.headlines.composables.SidePanel

@Composable
fun HeadlineScreen(){

    val headlines = remember { Headlines() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SidePanel(
        drawerState = drawerState,
        searchQuery = headlines.searchedText,
        onSearchQueryChange = headlines::updateSearchText,
        content = {
            HeadlineContent(
                openMenu = {
                    scope.launch { drawerState.open() }
                },
                headerTitle = headlines.title,
                articles = headlines.artilcles,
            )
        }
    )
}

@Composable
private fun HeadlineContent(
    headerTitle: String,
    articles: List<Article>,
    openMenu: () -> Unit,
){

    if(articles.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FilledIconButton(
                    onClick = openMenu,
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.List,
                            contentDescription = "Localized description",
                        )
                    }
                )

                Text(
                    text = headerTitle,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 400.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                items(articles) { article ->
                    ArticleCard(
                        article = article,
                        modifier = Modifier
                            .width(400.dp),
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Loading...")
        }
    }
}