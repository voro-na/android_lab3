package com.example.lab3.model

import com.google.gson.annotations.SerializedName

data class News(
    val title: String?,
    val link: String?,
    val content: String?,
)

data class NewsData (

    @SerializedName("results")
    val news: MutableList<News>?,
)