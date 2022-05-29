package com.sample.newsapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sample.newsapi.data.entity.News
import com.sample.newsapi.databinding.EachRowBinding
import javax.inject.Inject

class NewsAdapter @Inject constructor() : PagingDataAdapter<News, NewsAdapter.NewsViewHolder>(DiffUtils) {

    class NewsViewHolder(private val binding: EachRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(news: News){
            binding.apply {
                image.load(news.urlToImage)
                title.text = news.title
                title.text = news.description
            }
        }
    }

    object DiffUtils : DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return false
        }

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val dogs = getItem(position)
        if(dogs != null){
            holder.bind(dogs)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       return NewsViewHolder(EachRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}