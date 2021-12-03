package com.github.pedrodimoura.nuchallenge.shortener.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.pedrodimoura.nuchallenge.R
import com.github.pedrodimoura.nuchallenge.databinding.ShortenedUrlItemBinding
import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel

class ShortenedUrlsAdapter :
    ListAdapter<ShortUrlModel, ShortenedUrlsViewHolder>(ShortenedUrlDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortenedUrlsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.shortened_url_item, parent, false)
        return ShortenedUrlsViewHolder(ShortenedUrlItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ShortenedUrlsViewHolder, position: Int) =
        holder.bind(getItem(position))
}