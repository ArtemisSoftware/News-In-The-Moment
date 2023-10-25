package util

import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.net.URI
import java.net.URL
object UrlUtils {
    fun loadPicture(url: String) =
        Image.makeFromEncoded(URL(url).readBytes())
            .toComposeImageBitmap()
}