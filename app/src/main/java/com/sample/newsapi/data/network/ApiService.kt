package com.sample.newsapi.data.network

import com.sample.newsapi.data.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val BASE_URL = "https://newsapi.org/"
    }

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("apiKey") apiKey:String,
        @Query("pageSize") pageSize:String,
        @Query("page") page:String,
        @Query("q") query:String,
    ) : NewsResponse
}