package domain.models

enum class CountryCode(
    val code: String,
    val description: String,
) {
    USA(code = "us", description = "Us"),
    PORTUGAL(code = "pt", description = "Pt"),
}