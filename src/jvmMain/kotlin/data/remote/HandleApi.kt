package data.remote

import com.google.gson.Gson
import data.remote.dto.ErrorDto
import data.remote.exceptions.NewsApiException
import data.remote.exceptions.NewsException
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*

object HandleApi {

    suspend fun responseCheck(response: HttpResponse) {
        if (!response.status.isSuccess()) {
            throw NewsApiException(response.body())
        }
    }

    suspend fun <T> safeApiCall(callFunction: suspend () -> T): T {
        return try {
            callFunction.invoke()
        } catch (ex: Exception) {
            when (ex) {
                is NewsApiException -> {
                    throw NewsException(1, convertErrorBody(ex.message))
                }
                is JsonConvertException -> {
                    throw NewsException(2)
                }
                else -> {
                    throw NewsException(3)
                }
            }
        }
    }

    private fun convertErrorBody(responseBody: String?): String {
        return try {
            responseBody?.let { body ->
                Gson().fromJson(body, ErrorDto::class.java).message
            } ?: "Api communication error"
        } catch (exception: Exception) {
            "Api communication error"
        }
    }
}
