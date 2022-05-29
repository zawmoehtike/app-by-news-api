package com.sample.newsapi.data.response

import com.sample.newsapi.data.entity.News
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
{
            "source": {
                "id": "wired",
                "name": "Wired"
            },
            "author": "Arielle Pardes",
            "title": "Miami’s Bitcoin Conference Left a Trail of Harassment",
            "description": "For some women, inappropriate conduct from other conference-goers continued to haunt them online.",
            "url": "https://www.wired.com/story/bitcoin-2022-conference-harassment/",
            "urlToImage": "https://media.wired.com/photos/627a89e3e37e715cb7d760d2/191:100/w_1280,c_limit/Bitcoin_Miami_Biz_GettyImages-1239817123.jpg",
            "publishedAt": "2022-05-10T16:59:46Z",
            "content": "Now, even though there are a number of women-focused crypto spaces, Odeniran says women are still underrepresented. Ive been in spaces where Im the only Black person, or the only woman, or the only B… [+3828 chars]"
        }
 */
data class NewsVO(
    @SerializedName("source")
    @Expose
    val source: SourceVO? = null,
    @SerializedName("author")
    @Expose
    val author:String? = null,
    @SerializedName("title")
    @Expose
    val title:String? = null,
    @SerializedName("description")
    @Expose
    val description:String? = null,
    @SerializedName("url")
    @Expose
    val url:String? = null,
    @SerializedName("urlToImage")
    @Expose
    val urlToImage:String? = null,
    @SerializedName("content")
    @Expose
    val content:String? = null
) {
    fun toNews(): News {
        return News(
            author = author?:"",
            title = title?:"",
            description = description?:"",
            url = url?:"",
            urlToImage = urlToImage?:"",
            content = content?:""
        )
    }
}