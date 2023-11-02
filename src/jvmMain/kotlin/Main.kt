import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import data.repository.NewsRepositoryImpl
import di.appModule
import domain.repository.NewsRepository
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent
import presentation.headlines.HeadlineScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        HeadlineScreen()
    }
}

fun main() = application {
    startKoin {
        modules(appModule)
    }

    Window(
        onCloseRequest = ::exitApplication,
        rememberWindowState(size = DpSize(1200.dp, 1200.dp)),
        resizable = false,
    ) {
        App()
    }
}
