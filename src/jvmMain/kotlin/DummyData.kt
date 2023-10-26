import domain.models.Article

object DummyData {

    val article = Article(
        source = "The source",
        url = "https://paultan.org/2023/10/24/tesla-model-3-highland-long-range-facelift-in-malaysia-629-km-range-wltp-0-100-4-4s-price-from-rm218k/",
        imageUrl = "https://likedplaces.com/wp-content/uploads/2022/08/LikedPlaces-Jardim-botanico-tropical-scaled.jpg",
        title = "Tesla Model 3 Highland Long Range facelift in Malaysia – 629 km range WLTP, 0-100 4.4s; price from RM218k",
        content = "The Tesla Model 3 facelift, also dubbed Highland was launched in Malaysia last week, alongside the opening of the first Tesla Experience Centre in Malaysia in Pavilion Damansara Heights.\r\nThe Model 3",
    )

    val articles = listOf(article, article)
}