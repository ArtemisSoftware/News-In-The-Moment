
import domain.models.Article
import domain.models.News

object DummyData {

    val article = Article(
        source = "The source",
        title = "Tesla Model 3 Highland Long Range facelift in Malaysia – 629 km range WLTP, 0-100 4.4s; price from RM218k",
        content = "The Tesla Model 3 facelift, also dubbed Highland was launched in Malaysia last week, alongside the opening of the first Tesla Experience Centre in Malaysia in Pavilion Damansara Heights.\r\nThe Model 3",
    )

    val articles = listOf(article, article)
    val news = News(article, articles)
}
