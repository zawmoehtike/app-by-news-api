package com.sample.newsapi.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.newsapi.data.entity.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(list: List<News>)

    @Query("SELECT * FROM news")
    fun getAllNewsRaw():List<News>

    @Query("SELECT * FROM news")
    fun getAllNews():PagingSource<Int, News>

    @Query("DELETE FROM news")
    fun clearAllNews()
}