package com.example.lab3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.lab3.R
import com.example.lab3.model.News

class NewsAdapter(private val newsList: MutableList<News>) :

    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.header)
        val contentTextView: TextView = itemView.findViewById(R.id.content)
        val linkTextView: TextView = itemView.findViewById(R.id.link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.news, parent, false)

        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = newsList[position]

        holder.titleTextView.text = currentNews.title
        holder.contentTextView.text = currentNews.content
        holder.linkTextView.text = holder.linkTextView.context.getString(R.string.link_to_source, currentNews.link)
    }

    override fun getItemCount() = newsList.size

}