package presentation.headlines.mappers

import androidx.compose.ui.graphics.ImageBitmap
import domain.models.Article
import domain.models.ArticleType
import presentation.headlines.models.ArticleUI
import presentation.headlines.models.News

fun Article.toUI(type: ArticleType = ArticleType.STANDARD, defaultImage: ImageBitmap): ArticleUI {
    return ArticleUI(
        type = type,
        postUrl = url,
        title = title,
        content = content,
        source = source,
        imageUrl = imageUrl,
        default = defaultImage,
    )
}

fun Map<String, List<Article>>.toNews(defaultImage: ImageBitmap): List<News>{
    return this.map { (topic, articles) ->

        val headline = articles.first()
        val currentArticles = articles.drop(1)

        News(
            headline = headline.toUI(type = ArticleType.HEADLINE, defaultImage = defaultImage),
            articles = currentArticles.map { it.toUI(defaultImage = defaultImage) },
        )
    }
}
