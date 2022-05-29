package com.sample.newsapi.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.newsapi.data.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemote(list: List<RemoteKeys>)

    @Query("SELECT * FROM remoteKey WHERE repoId = :id")
    fun getRemoteKeys(id: Long) : RemoteKeys

    @Query("DELETE FROM remoteKey")
    fun clearAll()
}