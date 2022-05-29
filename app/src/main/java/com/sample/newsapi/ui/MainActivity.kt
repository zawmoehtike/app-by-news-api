package com.sample.newsapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.newsapi.adapter.NewsAdapter
import com.sample.newsapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var newsAdapter: NewsAdapter
    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intiRecyclerView()

        lifecycleScope.launchWhenStarted {
            mainViewModel.getAllNews().collectLatest { response->
                binding.apply {
                    recyclerview.isVisible = true
                    progressBar.isVisible = false
                }
                Log.d("main", "onCreate: $response")
                newsAdapter.submitData(response)
            }
        }

    }
    private fun intiRecyclerView() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = newsAdapter
            }
        }
    }
}