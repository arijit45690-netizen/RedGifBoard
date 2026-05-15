package com.yourname.redgifboard

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RedGifsApiService {

    @GET("v2/auth/temporary")
    suspend fun getToken(): TokenResponse

    @GET("v2/gifs/search")
    suspend fun searchGifs(
        @Header("Authorization") auth: String,
        @Query("search_text") query: String,
        @Query("count") count: Int = 20,
        @Query("page") page: Int = 1,
        @Query("order") order: String = "trending"
    ): SearchResponse
}

object RedGifsClient {
    val api: RedGifsApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.redgifs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RedGifsApiService::class.java)
    }
}
