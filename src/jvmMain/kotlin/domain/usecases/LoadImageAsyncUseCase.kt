package domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import javax.imageio.ImageIO

class LoadImageAsyncUseCase {

    suspend operator fun invoke(url: String) = withContext(Dispatchers.IO) {
        try {
            val imageUrl = URL(url)
            val bufferedImage = ImageIO.read(imageUrl)
            bufferedImage
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
