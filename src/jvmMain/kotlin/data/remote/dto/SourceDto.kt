package data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SourceDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String
)