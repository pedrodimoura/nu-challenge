package com.github.pedrodimoura.nuchallenge.shortener.presentation.adapter

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.pedrodimoura.nuchallenge.databinding.ShortenedUrlItemBinding
import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel

class ShortenedUrlsViewHolder(
    private val binding: ShortenedUrlItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(shortUrlModel: ShortUrlModel) {
        with(binding) {
            tvShortUrl.text = shortUrlModel.shortUrl
            tvOriginalUrl.text = shortUrlModel.originalUrl
            shortUrlRoot.setOnClickListener {
                Toast
                    .makeText(binding.root.context, shortUrlModel.shortUrl, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}