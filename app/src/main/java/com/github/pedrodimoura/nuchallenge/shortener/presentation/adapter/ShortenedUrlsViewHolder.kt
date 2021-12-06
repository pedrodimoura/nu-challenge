package com.github.pedrodimoura.nuchallenge.shortener.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.github.pedrodimoura.nuchallenge.databinding.ShortenedUrlItemBinding
import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel

class ShortenedUrlsViewHolder(
    private val binding: ShortenedUrlItemBinding,
    private val onItemSelected: OnItemSelected?,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(shortUrlModel: ShortUrlModel) {
        with(binding) {
            tvOriginalUrl.text = shortUrlModel.originalUrl
            tvShortUrl.text = shortUrlModel.shortUrl
            shortUrlRoot.setOnClickListener {
                onItemSelected?.invoke(shortUrlModel.shortUrl)
            }
        }
    }
}
