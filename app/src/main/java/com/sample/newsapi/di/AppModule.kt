package com.sample.newsapi.di

import android.app.Application
import androidx.room.Room
import com.sample.newsapi.data.database.NewsDatabase
import com.sample.newsapi.data.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesDatabase(context: Application) =
        Room.databaseBuilder(context, NewsDatabase::class.java,"NewsDatabase")
            .build()

    @Provides
    @Singleton
    fun providesNewsDao(newsDatabase: NewsDatabase) =
        newsDatabase.getNewsDao()

    @Provides
    @Singleton
    fun providesRemoteDao(newsDatabase: NewsDatabase) =
        newsDatabase.remoteKeyDao()

    @Provides
    @Singleton
    fun moshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()!!

    @Provides
    @Singleton
    fun providesRetrofit(moshi: Moshi) =
        Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)!!

}