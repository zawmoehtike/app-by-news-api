package com.sample.newsapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.sample.newsapi.data.database.NewsDatabase
import com.sample.newsapi.data.entity.News
import com.sample.newsapi.data.repository.NewsRemoteMediator
import com.sample.newsapi.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(private val db: NewsDatabase, private val apiService: ApiService) : ViewModel() {

    @ExperimentalPagingApi
    fun getAllNews() : Flow<PagingData<News>> = Pager(
        config = PagingConfig(50,enablePlaceholders = false),
        pagingSourceFactory = {db.getNewsDao().getAllNews()},
        remoteMediator = NewsRemoteMediator(db,apiService)
    ).flow.cachedIn(viewModelScope)
}