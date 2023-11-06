package data.remote.exceptions

data class NewsException(
    val code: Int,
    override val message: String? = "Network error",
) : RuntimeException()
