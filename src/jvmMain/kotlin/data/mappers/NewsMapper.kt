package data.mappers

import data.remote.dto.ArticleDto
import domain.models.Article
import domain.models.News

fun ArticleDto.toArticle(): Article {
    return Article(
        postUrl = url,
        imageUrl = urlToImage,
        title = title ?: "",
        content = content ?: "",
        source = source.name,
    )
}

fun List<ArticleDto>.toNews(): News {
    val headline = this.first()
    val currentArticles = if (this.isEmpty()) emptyList() else this.drop(1)
    return News(
        headline = headline.toArticle(),
        articles = currentArticles.map { it.toArticle() },
    )
}
