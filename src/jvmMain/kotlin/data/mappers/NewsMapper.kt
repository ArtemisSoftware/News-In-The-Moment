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
    val articles = this.filter { it.content != "[Removed]" }

    val headline = articles.first()
    val currentArticles = if (articles.isEmpty()) emptyList() else articles.drop(1)
    return News(
        headline = headline.toArticle(),
        articles = currentArticles.map { it.toArticle() },
    )
}
