package com.sample.newsapi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.newsapi.data.dao.NewsDao
import com.sample.newsapi.data.dao.RemoteKeysDao
import com.sample.newsapi.data.entity.News
import com.sample.newsapi.data.entity.RemoteKeys

@Database(entities = [News::class, RemoteKeys::class],version = 1,exportSchema = false)
abstract class NewsDatabase : RoomDatabase(){

    abstract fun getNewsDao(): NewsDao
    abstract fun remoteKeyDao(): RemoteKeysDao
}