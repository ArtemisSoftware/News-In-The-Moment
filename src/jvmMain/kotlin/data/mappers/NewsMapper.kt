package data.mappers

import data.remote.dto.ArticleDto
import domain.models.Article

fun ArticleDto.toArticle(): Article{
    return Article(
        url = url,
        imageUrl = urlToImage,
        title = title ?: "",
        content = content ?: "",
        source = source.name,
    )
}
