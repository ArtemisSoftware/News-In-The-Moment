package data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    @SerializedName("articles")
    val articles: List<ArticleDto>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
)