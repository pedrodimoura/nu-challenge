package com.github.knutwhitemane.nuchallenge.shortener.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.knutwhitemane.nuchallenge.R
import com.github.knutwhitemane.nuchallenge.databinding.ShortenedUrlItemBinding
import com.github.knutwhitemane.nuchallenge.shortener.domain.model.ShortUrlModel

typealias OnItemSelected = (String) -> Unit

class ShortenedUrlsAdapter :
    ListAdapter<ShortUrlModel, ShortenedUrlsViewHolder>(ShortenedUrlDiff()) {

    private var onItemSelected: OnItemSelected? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortenedUrlsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.shortened_url_item, parent, false)
        return ShortenedUrlsViewHolder(ShortenedUrlItemBinding.bind(view), this.onItemSelected)
    }

    override fun onBindViewHolder(holder: ShortenedUrlsViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun onItemSelected(onItemSelected: OnItemSelected) {
        this.onItemSelected = onItemSelected
    }
}