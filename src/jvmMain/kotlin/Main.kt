import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.appModule
import org.koin.core.context.GlobalContext.startKoin
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
        title = "News In The Moment",
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(size = DpSize(1200.dp, 1200.dp)),
        resizable = false,
    ) {
        App()
    }
}
