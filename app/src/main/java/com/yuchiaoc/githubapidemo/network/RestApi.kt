package com.yuchiaoc.githubapidemo.network

import com.google.gson.annotations.SerializedName
import com.yuchiaoc.githubapidemo.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface RestApi {
    /*
     * https://api.github.com/search/users?q=tom+in:name&page=4&per_page=5
     */
    @Headers("Accept: application/vnd.github.v3.text-match+json")
    @GET("/search/users")
    fun fetch(
        @Query("q", encoded = true) q: String?,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Call<Response>

    data class Response(
        @SerializedName("items") val users: List<User>
    )

    companion object {
        private const val BASE_URL = "https://api.github.com/"
        fun create(): RestApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestApi::class.java)
        }
    }
}