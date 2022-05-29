package com.sample.newsapi.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remoteKey")
data class RemoteKeys(
    @PrimaryKey
    val repoId: Long? = null,
    val prevKey:Int?,
    val nextKey:Int?
)
