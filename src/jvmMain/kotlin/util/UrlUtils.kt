package util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.net.URL
object UrlUtils {
    fun loadPicture(url: String): ImageBitmap =
        Image.makeFromEncoded(URL(url).readBytes())
            .toComposeImageBitmap()

    fun getBitmap(imageUrl: String): ImageBitmap?{
        return try {
            loadPicture(imageUrl)
        }
        catch (e: Exception){
            null
        }
    }
}