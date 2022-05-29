package com.sample.newsapi.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SourceVO(
    @SerializedName("id")
    @Expose
    val id:String? = null,
    @SerializedName("name")
    @Expose
    val name:String? = null,
)