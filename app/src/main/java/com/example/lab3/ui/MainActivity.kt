package com.example.lab3.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab3.R
import com.example.lab3.adapter.NewsAdapter
import com.example.lab3.api.NewsDataApiService
import com.example.lab3.databinding.MainActivityBinding
import com.example.lab3.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        supportActionBar?.hide()

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.newsRecyclerView.layoutManager = linearLayoutManager
        binding.newsRecyclerView.adapter = NewsAdapter(ArrayList())

        binding.searchButton.setOnClickListener {
            performSearch()
        }
    }

    private fun performSearch() {
        val query: String = binding.searchEditText.text.toString()

        if (query.isEmpty()) {
            showError()
            return
        }
        hideError()
        Log.i("MainActivity", "Search is performing: $query")

        val newsDataApiService: NewsDataApiService = NewsDataApiService.create()
        newsDataApiService
            .getNewsData("pub_35360762c6a363dbf1f2cacdac2a2b571e53e", query)
            .enqueue(object : Callback<NewsData> {

                override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                    val newsData: NewsData? = response.body()
                    Log.i("MainActivity", "Successful request. Total results: " + newsData?.news?.size)

                    if (newsData?.news?.size == 0) {
                        binding.resultsFoundTextView.text =
                            getString(R.string.results_found, newsData.news.size)
                        binding.resultsFoundTextView.visibility = View.VISIBLE
                    } else {
                        binding.resultsFoundTextView.visibility = View.INVISIBLE
                    }

                    binding.newsRecyclerView.adapter = newsData?.news?.let { NewsAdapter(it) }
                }

                override fun onFailure(call: Call<NewsData>, t: Throwable) {
                    Log.e("MainActivity", "Request " + call.request().url() + " has been failed", t)
                }
            })
    }

    private fun showError() {
        binding.errorMessage.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.errorMessage.visibility = View.INVISIBLE
    }
}
