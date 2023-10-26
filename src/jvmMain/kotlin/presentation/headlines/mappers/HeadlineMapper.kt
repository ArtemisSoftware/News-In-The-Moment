package presentation.headlines.mappers

import androidx.compose.ui.graphics.ImageBitmap
import domain.models.Article
import domain.models.ArticleType
import util.UrlUtils

fun Article.toUI(type: ArticleType = ArticleType.STANDARD, defaultImage: ImageBitmap): ArticleUI{
    return ArticleUI(
        type = type,
        url = url,
        title = title,
        content = content,
        source = source,
        image = imageUrl?.let { UrlUtils.getBitmap(it) } ?: run { defaultImage }
    )
}