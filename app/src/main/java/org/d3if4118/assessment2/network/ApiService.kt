package org.d3if4118.assessment2.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if4118.assessment2.model.News
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class ApiStatus { LOADING, SUCCESS, FAILED }

object ApiService {
    object NULL_TO_EMPTY_STRING_ADAPTER {
        @FromJson
        fun fromJson(reader: JsonReader): String {
            if (reader.peek() != JsonReader.Token.NULL) {
                return reader.nextString()
            }
            reader.nextNull<Unit>()
            return ""
        }
    }

    private val moshi: Moshi = Moshi.Builder()
        .add(NULL_TO_EMPTY_STRING_ADAPTER)
        .add(KotlinJsonAdapterFactory()).build()

    private val retrofit =
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("http://newsapi.org").build()

    val service: NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }

    interface NewsService {
        @GET("/v2/top-headlines")
        suspend fun getListNews(
            @Query("country") country: String?,
            @Query("category") category: String?,
            @Query("apiKey") apiKey: String?
        ): News
    }
}