package presentation.headlines.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import domain.usecases.LoadImageAsyncUseCase
import org.koin.java.KoinJavaComponent

@Composable
fun AsyncImage(
    imageUrl: String? = null,
    imageLib: HashMap<String?, ImageBitmap>,
    modifier: Modifier = Modifier,
    updateImageLib: (String, ImageBitmap?) -> Unit,
    loadImageAsyncUseCase: LoadImageAsyncUseCase = KoinJavaComponent.get(LoadImageAsyncUseCase::class.java),
) {
    var imageBitmap by remember() { mutableStateOf<ImageBitmap?>(null) }
    var loading by rememberSaveable { mutableStateOf(true) }
    val tempUrl = imageUrl ?: "no_image"

    if (imageLib[tempUrl] == null) {
        imageUrl?.let { url ->
            LaunchedEffect(url) {
                val loadedImage = loadImageAsyncUseCase(url)
                imageBitmap = loadedImage?.toComposeImageBitmap()
                updateImageLib(url, imageBitmap)
                loading = false
            }
        } ?: run {
            loading = false
        }
    } else {
        loading = false
    }
    AnimatedVisibility(
        visible = !loading && (imageLib[tempUrl] != null),
    ) {
        imageLib[tempUrl]?.let { image ->
            Image(
                bitmap = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier,
            )
        }
    }
}
