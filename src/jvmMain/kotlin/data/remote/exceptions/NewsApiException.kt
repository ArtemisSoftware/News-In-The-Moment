package data.remote.exceptions

class NewsApiException(
    override val message: String? = "Network error",
) : RuntimeException()
