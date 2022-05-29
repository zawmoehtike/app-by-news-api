package com.sample.newsapi.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
{
    "status": "ok",
    "code": "apiKeyMissing",
    "message": "Your API key is missing. Append this to the URL with the apiKey param, or use the x-api-key HTTP header."
    "totalResults": 14462,
    "articles": []
 */
data class NewsResponse(
    @SerializedName("status")
    @Expose
    var status:String? = null,
    @SerializedName("code")
    @Expose
    var code:String? = null,
    @SerializedName("message")
    @Expose
    var message:String? = null,
    @SerializedName("totalResults")
    @Expose
    var totalResults:String? = null,
    @SerializedName("articles")
    @Expose
    var articles: List<NewsVO>? = null
)