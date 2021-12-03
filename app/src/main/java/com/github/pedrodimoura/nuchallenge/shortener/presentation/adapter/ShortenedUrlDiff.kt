package com.github.pedrodimoura.nuchallenge.shortener.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.pedrodimoura.nuchallenge.shortener.domain.model.ShortUrlModel

class ShortenedUrlDiff : DiffUtil.ItemCallback<ShortUrlModel>() {
    override fun areContentsTheSame(oldItem: ShortUrlModel, newItem: ShortUrlModel): Boolean =
        oldItem.alias == newItem.alias

    override fun areItemsTheSame(oldItem: ShortUrlModel, newItem: ShortUrlModel): Boolean =
        oldItem == newItem
}