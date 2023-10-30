package util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.net.URL
import java.net.URI
import java.awt.Desktop

object UrlUtils {
    private fun loadPicture(url: String): ImageBitmap =
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

    fun openURL(url: String) {
        val desktop = Desktop.getDesktop()
        desktop.browse(URI(url))
    }
}