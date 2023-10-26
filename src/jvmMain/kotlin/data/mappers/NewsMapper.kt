package data.mappers

import data.remote.dto.ArticleDto
import domain.models.Article
import util.UrlUtils.getBitmap

fun ArticleDto.toArticle(): Article{
    return Article(
        url = url,
        imageUrl = urlToImage,
        title = title ?: "",
        content = content ?: "",
        source = source.name,
        image = urlToImage?.let { getBitmap(urlToImage) } ?: run { null }
    )
}
