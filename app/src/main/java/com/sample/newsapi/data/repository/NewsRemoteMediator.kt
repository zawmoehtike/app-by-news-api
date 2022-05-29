package com.sample.newsapi.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sample.newsapi.data.database.NewsDatabase
import com.sample.newsapi.data.entity.News
import com.sample.newsapi.data.entity.RemoteKeys
import com.sample.newsapi.data.network.ApiService
import com.sample.newsapi.util.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
@ExperimentalPagingApi
class NewsRemoteMediator constructor(
    private val db: NewsDatabase,
    private val apiService: ApiService
)  : RemoteMediator<Int, News>() {
    private val STARTING_PAGE_INDEX = 1

    override suspend fun initialize(): InitializeAction {
       return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, News>): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page  = when(pageKeyData){
            is MediatorResult.Success ->{
                return pageKeyData
            }else->{
                pageKeyData as Int
            }
        }

      try {
          val response = apiService.getAllNews(
              API_KEY,
              state.config.pageSize.toString(),
              page.toString(),
              "bitcoin"
          )
          val endOfList = response.articles?.isEmpty()
          db.withTransaction {
              if(loadType == LoadType.REFRESH){
                  db.remoteKeyDao().clearAll()
                  db.getNewsDao().clearAllNews()
              }
              val prevKey = if (page == STARTING_PAGE_INDEX) null else page-1
              val nextKey = if(endOfList == true) null else page+1
              val articles = response.articles?.map {
                  it.toNews()
              }
              db.getNewsDao().insertNews(articles?:ArrayList(0))

              val keys = db.getNewsDao().getAllNewsRaw().map {
                  RemoteKeys(it.id, prevKey, nextKey)
              }
              db.remoteKeyDao().insertRemote(keys?:ArrayList(0))
          }
         return MediatorResult.Success(endOfPaginationReached = endOfList?:true)
      }catch (e:IOException){
       return   MediatorResult.Error(e)
      }catch (e:HttpException){
          return MediatorResult.Error(e)
      }
    }


    private suspend fun getKeyPageData(loadType: LoadType,state: PagingState<Int, News>) : Any{
        return when(loadType){
            LoadType.REFRESH->{
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextKey?.minus(1)?:STARTING_PAGE_INDEX
            }
            LoadType.PREPEND->{
                val remoteKeys = getFirstRemoteKey(state)
               val prevKey = remoteKeys?.prevKey ?:MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND->{
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey ?:MediatorResult.Success(
                    endOfPaginationReached = true
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, News>) : RemoteKeys? {
        return withContext(Dispatchers.IO){
            state.pages
                .firstOrNull { it.data.isNotEmpty() }
                ?.data?.firstOrNull()
                ?.let { new -> db.remoteKeyDao().getRemoteKeys(new.id!!)}
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, News>) : RemoteKeys? {
        return withContext(Dispatchers.IO){
            state.pages
                .lastOrNull{it.data.isNotEmpty()}
                ?.data?.lastOrNull()
                ?.let { new -> db.remoteKeyDao().getRemoteKeys(new.id!!) }
        }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, News>) : RemoteKeys? {
        return withContext(Dispatchers.IO){
            state.anchorPosition?.let { position->
                state.closestItemToPosition(position)?.id?.let {repId->
                    db.remoteKeyDao().getRemoteKeys(repId)
                }
            }
        }
    }

}
