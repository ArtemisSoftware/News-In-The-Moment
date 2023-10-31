package domain.usecases

class GetTopicsUseCase {

    operator fun invoke() = listOf(
        "general", "business", "technology", "entertainment",  "health", "science", "sports",
    )
}